package hu.unideb.inf.books_backend.repository;

import hu.unideb.inf.books_backend.model.Book;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository
    extends JpaRepository<Book, Integer> {

    List<Book> findAll();

    Optional<Book> findById(UUID id);

    void deleteById(@NonNull UUID id);
}
