package hu.unideb.inf.books_backend.service.author;

import hu.unideb.inf.books_backend.model.Author;
import hu.unideb.inf.books_backend.model.Book;
import hu.unideb.inf.books_backend.service.author.dto.AuthorBookRequest;
import hu.unideb.inf.books_backend.service.author.dto.AuthorBookResponse;
import hu.unideb.inf.books_backend.service.common.CommonAuthorBookService;
import hu.unideb.inf.books_backend.service.common.CommonAuthorService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorBookServiceImpl implements AuthorBookService {

    private final CommonAuthorBookService commonAuthorBookService;
    private final CommonAuthorService commonAuthorService;

    @Override
    public List<AuthorBookResponse> getBooksForAuthor(@NonNull String username) {
        return commonAuthorBookService.getBooksForAuthor(username).stream()
                .map(AuthorBookResponse::of)
                .toList();
    }

    @Override
    public List<AuthorBookResponse> getAllBooks() {
        return commonAuthorBookService.getAll().stream()
                .map(AuthorBookResponse::of)
                .toList();
    }

    @Override
    public AuthorBookResponse getBook(@NonNull UUID bookID) {
        return commonAuthorBookService.getBookById(bookID)
                .map(AuthorBookResponse::of)
                .orElseThrow(() -> new IllegalArgumentException("Book Not Found"));
    }

    @Override
    public List<AuthorBookResponse> searchBooks(@NonNull Optional<String> title, @NonNull Optional<String> author) {
        return commonAuthorBookService.search(title, author).stream()
                .map(AuthorBookResponse::of)
                .toList();
    }

    @Override
    public AuthorBookResponse createBook(@NonNull AuthorBookRequest authorBookRequest, @NonNull String username) {
        Set<Author> bookAuthors = authorBookRequest.getAuthorUsernames().stream()
                .map(authorUsername -> commonAuthorService.findOne(authorUsername)
                        .orElseThrow(() -> new IllegalArgumentException("Author not found: " + authorUsername)))
                .collect(Collectors.toSet());

        bookAuthors.add(commonAuthorService.findOne(username).
                orElseThrow(() -> new IllegalArgumentException("Author not found: " + username)));

        var book = Book.builder()
                .authors(bookAuthors)
                .title(authorBookRequest.getTitle())
                .publishDate(authorBookRequest.getPublishDate())
                .build();

        var savedBook = commonAuthorBookService.createOne(book);

        return AuthorBookResponse.of(savedBook);
    }

    @Override
    public AuthorBookResponse updateBook(@NonNull UUID bookID, @NonNull AuthorBookRequest authorBookRequest, @NonNull String username) {
        Book book = commonAuthorBookService.getBookById(bookID)
                .orElseThrow(() -> new IllegalArgumentException("Book Not Found"));

        boolean isUserAnAuthor = book.getAuthors().stream()
                .anyMatch(author -> author.getUsername().equals(username));

        if (!isUserAnAuthor) {
            throw new AccessDeniedException("User " + username + " is not authorized to update this book");
        }

        Set<Author> newAuthors = authorBookRequest.getAuthorUsernames().stream()
                .map(authorUsername -> commonAuthorService.findOne(authorUsername)
                        .orElseThrow(() -> new IllegalArgumentException("Author not found")))
                .collect(Collectors.toSet());

        book.setAuthors(newAuthors);
        book.setTitle(authorBookRequest.getTitle());
        book.setPublishDate(authorBookRequest.getPublishDate());

        var updated = commonAuthorBookService.updateOne(book);

        return AuthorBookResponse.of(updated);
    }

    @Override
    public void deleteByUUID(@NonNull UUID bookID, @NonNull String username) {
        Book book = commonAuthorBookService.getBookById(bookID)
                .orElseThrow(() -> new IllegalArgumentException("Book Not Found"));

        boolean isUserAnAuthor = book.getAuthors().stream()
                .anyMatch(author -> author.getUsername().equals(username));

        if (!isUserAnAuthor) {
            throw new AccessDeniedException("User " + username + " is not authorized to delete this book");
        }

        commonAuthorBookService.deleteByUUID(bookID);
    }
}
