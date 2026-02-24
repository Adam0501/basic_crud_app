package hu.unideb.inf.books_backend.service.common;

import hu.unideb.inf.books_backend.model.Author;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommonAuthorService
    extends UserDetailsService {
    
    List<Author> getAll();

    Optional<Author> getOne(@NonNull UUID authorId);

    Optional<Author> findOne(@NonNull String username);
    
    List<Author> searchByUsername(@NonNull String username);
    
    Author createOne(@NonNull Author author);
    
    Author updateOne(@NonNull Author author);
    
    void deleteById(@NonNull UUID authorId);
}
