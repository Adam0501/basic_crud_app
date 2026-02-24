package hu.unideb.inf.books_backend.web.author;

import hu.unideb.inf.books_backend.service.author.dto.AuthorBookRequest;
import hu.unideb.inf.books_backend.service.author.dto.AuthorBookResponse;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public interface AuthorBookController {

    @GetMapping(value = "/api/author/me/book")
    List<AuthorBookResponse> getBooksForAuthor(@NonNull Principal principal);

    @GetMapping(value = "/books")
    List<AuthorBookResponse> getAllBooks();

    @GetMapping(value = "/books/{bookID}")
    AuthorBookResponse getBook(@NonNull @PathVariable UUID bookID);

    @GetMapping(value = "/search")
    List<AuthorBookResponse> searchBooks(@NonNull @RequestParam Optional<String> title, @NonNull @RequestParam Optional<String> author);

    @PostMapping(value = "/api/author/me/book")
    AuthorBookResponse createBook(@NonNull @RequestBody AuthorBookRequest authorBookRequest, @NonNull Principal principal);

    @PutMapping(value = "/api/author/me/book/{bookID}")
    AuthorBookResponse updateBook(@NonNull @PathVariable UUID bookID, @NonNull @RequestBody AuthorBookRequest authorBookRequest, @NonNull Principal principal);

    @DeleteMapping(value = "/api/author/me/book/{bookID}")
    void deleteByUUID(@NonNull @PathVariable UUID bookID, @NonNull Principal principal);
}
