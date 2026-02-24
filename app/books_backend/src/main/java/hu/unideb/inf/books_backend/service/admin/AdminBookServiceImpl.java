package hu.unideb.inf.books_backend.service.admin;


import hu.unideb.inf.books_backend.model.Author;
import hu.unideb.inf.books_backend.model.Book;
import hu.unideb.inf.books_backend.service.admin.dto.AdminBookRequest;
import hu.unideb.inf.books_backend.service.common.CommonAuthorBookService;
import hu.unideb.inf.books_backend.service.common.CommonAuthorService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminBookServiceImpl implements AdminBookService {

    private final CommonAuthorBookService commonAuthorBookService;
    private final CommonAuthorService commonAuthorService;

    @Override
    public Book getOne(@NonNull UUID id) {
        return commonAuthorBookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + id + " not found"));
    }

    @Override
    public List<Book> getAll() {
        return commonAuthorBookService.getAll();
    }

    @Override
    public Book createBook(@NonNull AdminBookRequest adminBookRequest) {
        Set<Author> incomingAuthors = adminBookRequest.getAuthorUsernames().stream()
                .map(username -> commonAuthorService.findOne(username)
                        .orElseThrow(() -> new IllegalArgumentException("Author with " + username + " username not found."))
                ).collect(Collectors.toSet());

        var book = Book.builder()
                .title(adminBookRequest.getTitle())
                .publishDate(adminBookRequest.getPublishDate())
                .authors(incomingAuthors)
                .build();

        return commonAuthorBookService.createOne(book);
    }

    @Override
    public Book updateBook(@NonNull UUID bookId, @NonNull AdminBookRequest adminBookRequest) {
        var existingBook = commonAuthorBookService.getBookById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + bookId + " not found"));

        Set<Author> incomingAuthors = adminBookRequest.getAuthorUsernames().stream()
                        .map(username -> commonAuthorService.findOne(username)
                                .orElseThrow(() -> new IllegalArgumentException("Author with " + username + " username not found.")))
                .collect(Collectors.toSet());

        existingBook.setTitle(adminBookRequest.getTitle());
        existingBook.setPublishDate(adminBookRequest.getPublishDate());
        existingBook.setAuthors(incomingAuthors);

        return commonAuthorBookService.updateOne(existingBook);
    }

    @Override
    public void deleteBook(@NonNull UUID bookId) {
        commonAuthorBookService.deleteByUUID(bookId);
    }
}
