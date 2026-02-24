package hu.unideb.inf.books_backend.repository;

import hu.unideb.inf.books_backend.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository
    extends JpaRepository<Author, UUID> {

    List<Author> findAll();

    Optional<Author> findByEmail(String email);

    Optional<Author> findByUsername(String username);
}
