package hu.unideb.inf.books_backend.service.admin;

import hu.unideb.inf.books_backend.model.Author;
import hu.unideb.inf.books_backend.model.Book;
import hu.unideb.inf.books_backend.service.admin.dto.AdminAuthor;
import hu.unideb.inf.books_backend.service.admin.dto.AdminAuthorRequest;
import hu.unideb.inf.books_backend.service.common.CommonAuthorBookService;
import hu.unideb.inf.books_backend.service.common.CommonAuthorService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminAuthorServiceImpl implements AdminAuthorService {

    private final CommonAuthorService commonAuthorService;
    private final PasswordEncoder passwordEncoder;
    private final CommonAuthorBookService commonAuthorBookService;

    @Override
    public AdminAuthor getOne(@NonNull UUID authorId) {
        return commonAuthorService.getOne(authorId)
                .map(AdminAuthor::from)
                .orElseThrow(() -> new IllegalArgumentException("Author not found."));
    }

    @Override
    public List<AdminAuthor> getAll() {
        return commonAuthorService.getAll().stream()
                .map(AdminAuthor::from)
                .toList();
    }

    @Override
    public AdminAuthor createOne(@NonNull AdminAuthorRequest adminAuthorRequest) {
        return Optional.ofNullable(
                    Author.builder()
                            .name(adminAuthorRequest.getName())
                            .id(adminAuthorRequest.getId())
                            .email(adminAuthorRequest.getEmail())
                            .admin(adminAuthorRequest.isAdmin())
                            .username(adminAuthorRequest.getUsername())
                            .password(passwordEncoder.encode(adminAuthorRequest.getUsername()))
                            .build()
                )
                .map(commonAuthorService::createOne)
                .map(AdminAuthor::from)
                .orElseThrow(() -> new IllegalArgumentException("Error during author creation."));
    }

    @Override
    public AdminAuthor updateOne(AdminAuthorRequest adminAuthorRequest) {
        Author existingAuthor = commonAuthorService.getOne(adminAuthorRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found."));

        Set<Book> newBooks;

        if (adminAuthorRequest.getBookIDList() != null && !adminAuthorRequest.getBookIDList().isEmpty()) {
            newBooks = adminAuthorRequest.getBookIDList().stream()
                    .map(id -> commonAuthorBookService.getBookById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Book with id " + id + " not found.")))
                    .collect(Collectors.toSet());
        } else {
            newBooks = new HashSet<>();
        }

        Set<Book> oldBooks = existingAuthor.getBooks();

        for (Book oldBook : oldBooks) {
            if (!newBooks.contains(oldBook)) {
                oldBook.getAuthors().remove(existingAuthor);
                commonAuthorBookService.updateOne(oldBook);
            }
        }

        for (Book newBook : newBooks) {
            if (!oldBooks.contains(newBook)) {
                newBook.getAuthors().add(existingAuthor);
                commonAuthorBookService.updateOne(newBook);
            }
        }

        existingAuthor.getBooks().clear();
        existingAuthor.getBooks().addAll(newBooks);

        existingAuthor.setAdmin(adminAuthorRequest.isAdmin());
        existingAuthor.setEmail(adminAuthorRequest.getEmail());
        existingAuthor.setUsername(adminAuthorRequest.getUsername());
        existingAuthor.setName(adminAuthorRequest.getName());

        Author updatedAuthor = commonAuthorService.updateOne(existingAuthor);

        return AdminAuthor.from(updatedAuthor);
    }

    @Override
    public List<AdminAuthor> search(@NonNull String username) {
        return commonAuthorService.searchByUsername(username).stream()
                .map(AdminAuthor::from)
                .toList();
    }

    @Override
    public void deleteOne(@NonNull UUID authorId) {
        var author = commonAuthorService.getOne(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found."));

        if (!author.getBooks().isEmpty()) {
            throw new IllegalStateException("Author has books.");
        }

        commonAuthorService.deleteById(authorId);
    }
}
