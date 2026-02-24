package hu.unideb.inf.books_backend.service.author;

import hu.unideb.inf.books_backend.service.author.dto.AuthorAuthorRequest;
import hu.unideb.inf.books_backend.service.author.dto.AuthorAuthorResponse;
import hu.unideb.inf.books_backend.service.common.CommonAuthorService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class AuthorAuthorServiceImpl
    implements AuthorAuthorService {

    private final CommonAuthorService commonAuthorService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER =  LoggerFactory.getLogger(AuthorAuthorServiceImpl.class);

    @Override
    public AuthorAuthorResponse findOne(@NonNull Principal principal) {
        LOGGER.debug("AuthorAuthorServiceImpl.findOne({})", principal.getName());
        return commonAuthorService.findOne(principal.getName())
                .map(AuthorAuthorResponse::from).orElseThrow(() -> new IllegalArgumentException("Author Not Found"));
    }

    @Override
    public AuthorAuthorResponse updateOne(@NonNull Principal principal, @NonNull AuthorAuthorRequest update) {
        return commonAuthorService.findOne(principal.getName())
                .map(original -> original.withUsername(update.getUsername())
                        .withEmail(update.getEmail())
                        .withPassword(passwordEncoder.encode(update.getPassword())))
                .map(commonAuthorService::updateOne)
                .map(AuthorAuthorResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Author Not Found"));
    }

    @Override
    public List<AuthorAuthorResponse> getAllExceptMe(@NonNull Principal principal) {
        return commonAuthorService.getAll().stream()
                .filter(author -> !Objects.equals(author.getUsername(), principal.getName()))
                .map(AuthorAuthorResponse::from)
                .toList();
    }
}
