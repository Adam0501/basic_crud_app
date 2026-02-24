package hu.unideb.inf.books_backend.runner;

import com.github.javafaker.Faker;
import hu.unideb.inf.books_backend.model.Book;
import hu.unideb.inf.books_backend.repository.AuthorRepository;
import hu.unideb.inf.books_backend.repository.BookRepository;
import hu.unideb.inf.books_backend.util.RandomPublishDate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

@Component
@AllArgsConstructor
@Order(2)
@Slf4j
public class BookRunner
    implements CommandLineRunner {

    private static final Faker FAKER = new Faker();
    private static final Logger LOGGER = LoggerFactory.getLogger(BookRunner.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var authors = authorRepository.findAll();

        for (int i = 0; i < 15; i++) {
            var bookData = FAKER.book();

            int randomSubListSize = (int)(Math.random() * 5 + 1);
            Collections.shuffle(authors);
            var selectedAuthors = authors.subList(0, randomSubListSize);

            var book = bookRepository.save(
                    Book.builder()
                            .title(bookData.title())
                            .authors(Set.copyOf(selectedAuthors))
                            .publishDate(RandomPublishDate.getRandomPublishDate())
                            .build()
            );

            for (var author : selectedAuthors) {
                var books = author.getBooks();
                books.add(book);
            }

            authorRepository.saveAll(authors);

            LOGGER.info("Book {} has been created", book.getTitle());
        }

        authorRepository.saveAll(authors);
    }

}
