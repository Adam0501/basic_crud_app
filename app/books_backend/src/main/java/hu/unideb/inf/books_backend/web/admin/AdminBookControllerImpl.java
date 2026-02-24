package hu.unideb.inf.books_backend.web.admin;

import hu.unideb.inf.books_backend.model.Book;
import hu.unideb.inf.books_backend.service.admin.AdminBookService;
import hu.unideb.inf.books_backend.service.admin.dto.AdminBookRequest;
import hu.unideb.inf.books_backend.service.author.AuthorBookService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class AdminBookControllerImpl implements AdminBookController {

    private final AdminBookService adminBookService;

    @Override
    public Book getOne(@NonNull UUID bookId) {
        return adminBookService.getOne(bookId);
    }

    @Override
    public List<Book> getAll() {
        return adminBookService.getAll();
    }

    @Override
    public Book createBook(@NonNull AdminBookRequest adminBookRequest) {
        return adminBookService.createBook(adminBookRequest);
    }

    @Override
    public Book updateBook(@NonNull UUID bookId, @NonNull AdminBookRequest adminBookRequest) {
        return adminBookService.updateBook(bookId, adminBookRequest);
    }

    @Override
    public void deleteBook(@NonNull UUID bookId) {
        adminBookService.deleteBook(bookId);
    }
}
