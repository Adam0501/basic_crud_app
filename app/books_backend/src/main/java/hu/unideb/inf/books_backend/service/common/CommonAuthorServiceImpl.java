package hu.unideb.inf.books_backend.service.common;

import hu.unideb.inf.books_backend.model.Author;
import hu.unideb.inf.books_backend.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CommonAuthorServiceImpl implements CommonAuthorService {

    private AuthorRepository authorRepository;

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> getOne(@NonNull UUID authorId) {
        return authorRepository.findById(authorId);
    }

    @Override
    public Optional<Author> findOne(@NonNull String username) {
        return authorRepository.findByUsername(username);
    }

    @Override
    public List<Author> searchByUsername(@NonNull String username) {
        return authorRepository.findAll().stream()
                .filter(author -> author.getUsername().contains(username))
                .toList();
    }

    @Override
    public Author createOne(@NonNull Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author updateOne(@NonNull Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteById(@NonNull UUID id) {
        authorRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authorRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
