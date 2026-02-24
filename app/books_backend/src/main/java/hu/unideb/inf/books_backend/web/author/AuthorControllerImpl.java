package hu.unideb.inf.books_backend.web.author;

import hu.unideb.inf.books_backend.service.author.AuthorAuthorService;
import hu.unideb.inf.books_backend.service.author.dto.AuthorAuthorRequest;
import hu.unideb.inf.books_backend.service.author.dto.AuthorAuthorResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class AuthorControllerImpl
    implements AuthorController {

    private final AuthorAuthorService authorAuthorService;

    @Override
    public AuthorAuthorResponse findOne(@NonNull Principal principal) {
        return authorAuthorService.findOne(principal);
    }

    @Override
    public List<AuthorAuthorResponse> getAllAuthorsExceptMe(@NonNull Principal principal) {
        return authorAuthorService.getAllExceptMe(principal);
    }

    @Override
    public AuthorAuthorResponse updateOne(@NonNull Principal principal, AuthorAuthorRequest authorAuthorRequest) {
        return authorAuthorService.updateOne(principal, authorAuthorRequest);
    }
}
