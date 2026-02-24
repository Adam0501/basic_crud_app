package hu.unideb.inf.books_backend.service.author;

import hu.unideb.inf.books_backend.service.author.dto.AuthorBookRequest;
import hu.unideb.inf.books_backend.service.author.dto.AuthorBookResponse;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorBookService {

    List<AuthorBookResponse> getBooksForAuthor(@NonNull String username);

    List<AuthorBookResponse> getAllBooks();

    AuthorBookResponse getBook(@NonNull UUID bookID);

    List<AuthorBookResponse> searchBooks(@NonNull Optional<String> title, @NonNull Optional<String> author);

    AuthorBookResponse createBook(@NonNull AuthorBookRequest authorBookRequest, @NonNull String username);

    AuthorBookResponse updateBook(@NonNull UUID bookID, @NonNull AuthorBookRequest authorBookRequest, @NonNull String username);

    void deleteByUUID(@NonNull UUID bookID, @NonNull String username);
}
