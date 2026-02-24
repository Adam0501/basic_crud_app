package hu.unideb.inf.books_backend.service.common;

import hu.unideb.inf.books_backend.model.Book;
import hu.unideb.inf.books_backend.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CommonAuthorBookServiceImpl implements CommonAuthorBookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(UUID id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getBooksForAuthor(@NonNull String username) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getAuthors().stream()
                        .anyMatch(author -> author.getUsername().equalsIgnoreCase(username)))
                .toList();
    }

    @Override
    public List<Book> search(@NonNull Optional<String> title, @NonNull Optional<String> authorName) {
        return bookRepository.findAll().stream()
                .filter(
                        book -> title
                                .map(t -> book.getTitle().toLowerCase().contains(t.toLowerCase()))
                                .orElse(true)
                )
                .filter(
                        book -> authorName
                                .map(name -> book.getAuthors().stream()
                                        .anyMatch(author -> author.getName().toLowerCase().contains(name.toLowerCase())))
                                .orElse(true)
                )
                .toList();
    }

    @Override
    @Transactional
    public void deleteByUUID(@NonNull UUID id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book Not Found"));

        book.getAuthors().forEach(author -> author.getBooks().remove(book));
        book.getAuthors().clear();

        bookRepository.deleteById(id);
    }

    @Override
    public Book createOne(@NonNull Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateOne(@NonNull Book book) {
        return bookRepository.save(book);
    }
}
