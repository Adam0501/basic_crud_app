package hu.unideb.inf.books_backend.service.common;

import hu.unideb.inf.books_backend.model.Book;
import lombok.NonNull;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommonAuthorBookService {

    List<Book> getAll();

    Optional<Book> getBookById(UUID id);

    List<Book> getBooksForAuthor(@NonNull String username);

    List<Book> search(@NonNull Optional<String> title,
        @NonNull Optional<String> authorName);

    void deleteByUUID(@NonNull UUID id);

    Book createOne(@NonNull Book book);

    Book updateOne(@NonNull Book book);
}
