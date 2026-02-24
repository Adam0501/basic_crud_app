package hu.unideb.inf.books_backend.service.admin;

import hu.unideb.inf.books_backend.model.Book;
import hu.unideb.inf.books_backend.service.admin.dto.AdminBookRequest;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface AdminBookService {

    Book getOne(@NonNull UUID id);

    List<Book> getAll();

    Book createBook(@NonNull AdminBookRequest adminBookRequest);

    Book updateBook(@NonNull UUID bookId, @NonNull AdminBookRequest adminBookRequest);

    void deleteBook(@NonNull UUID bookId);
}
