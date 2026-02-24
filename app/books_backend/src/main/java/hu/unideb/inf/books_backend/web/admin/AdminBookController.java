package hu.unideb.inf.books_backend.web.admin;

import hu.unideb.inf.books_backend.model.Book;
import hu.unideb.inf.books_backend.service.admin.dto.AdminBookRequest;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public interface AdminBookController {

    @GetMapping(value = "/api/admin/book/{bookId}")
    Book getOne(@NonNull @PathVariable UUID bookId);

    @GetMapping(value = "/api/admin/book")
    List<Book> getAll();

    @PostMapping(value = "/api/admin/book")
    Book createBook(@NonNull @RequestBody AdminBookRequest adminBookRequest);

    @PutMapping(value = "/api/admin/book/{bookId}")
    Book updateBook(@NonNull @PathVariable UUID bookId, @RequestBody AdminBookRequest adminBookRequest);

    @DeleteMapping(value = "api/admin/book/{bookId}")
    void deleteBook(@NonNull @PathVariable UUID bookId);
}
