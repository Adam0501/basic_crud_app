package hu.unideb.inf.books_backend.service.author;

import hu.unideb.inf.books_backend.service.author.dto.AuthorAuthorRequest;
import hu.unideb.inf.books_backend.service.author.dto.AuthorAuthorResponse;
import jakarta.transaction.Transactional;
import lombok.NonNull;

import java.security.Principal;
import java.util.List;

public interface AuthorAuthorService {

    AuthorAuthorResponse findOne(@NonNull Principal principal);

    @Transactional
    AuthorAuthorResponse updateOne(@NonNull Principal principal, @NonNull AuthorAuthorRequest authorAuthorRequest);

    List<AuthorAuthorResponse> getAllExceptMe(@NonNull Principal principal);
}
