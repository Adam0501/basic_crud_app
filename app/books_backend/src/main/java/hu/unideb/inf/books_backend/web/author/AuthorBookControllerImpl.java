package hu.unideb.inf.books_backend.web.author;

import hu.unideb.inf.books_backend.service.author.AuthorBookService;
import hu.unideb.inf.books_backend.service.author.dto.AuthorBookRequest;
import hu.unideb.inf.books_backend.service.author.dto.AuthorBookResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@AllArgsConstructor
@Slf4j
public class AuthorBookControllerImpl
    implements AuthorBookController {

    private final AuthorBookService authorBookService;

    @Override
    public List<AuthorBookResponse> getBooksForAuthor(@NonNull Principal principal) {
        return authorBookService.getBooksForAuthor(principal.getName());
    }

    @Override
    public List<AuthorBookResponse> getAllBooks() {
        return authorBookService.getAllBooks();
    }

    @Override
    public AuthorBookResponse getBook(@NonNull UUID bookID) {
        return authorBookService.getBook(bookID);
    }

    @Override
    public List<AuthorBookResponse> searchBooks(@NonNull Optional<String> title, @NonNull Optional<String> author) {
        return authorBookService.searchBooks(title, author);
    }

    @Override
    public AuthorBookResponse createBook(@NonNull AuthorBookRequest authorBookRequest, @NonNull Principal principal) {
        return authorBookService.createBook(authorBookRequest, principal.getName());
    }

    @Override
    public AuthorBookResponse updateBook(@NonNull UUID bookID, @NonNull AuthorBookRequest authorBookRequest, @NonNull Principal principal) {
        return authorBookService.updateBook(bookID, authorBookRequest, principal.getName());
    }

    @Override
    public void deleteByUUID(@NonNull UUID bookID, @NonNull Principal principal) {
        authorBookService.deleteByUUID(bookID, principal.getName());
    }

}
