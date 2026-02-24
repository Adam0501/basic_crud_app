package hu.unideb.inf.books_backend.web.author;

import hu.unideb.inf.books_backend.service.author.dto.AuthorAuthorRequest;
import hu.unideb.inf.books_backend.service.author.dto.AuthorAuthorResponse;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public interface AuthorController {

    @GetMapping(value = "/api/author/me")
    AuthorAuthorResponse findOne(@NonNull Principal principal);

    @GetMapping(value = "/api/author")
    List<AuthorAuthorResponse> getAllAuthorsExceptMe(@NonNull Principal principal);

    @PutMapping(value = "/api/author/me")
    AuthorAuthorResponse updateOne(@NonNull Principal principal, @NonNull @RequestBody AuthorAuthorRequest authorAuthorRequest);
}
