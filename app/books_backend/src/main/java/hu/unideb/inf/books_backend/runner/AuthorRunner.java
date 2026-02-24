package hu.unideb.inf.books_backend.runner;

import com.github.javafaker.Faker;
import hu.unideb.inf.books_backend.model.Author;
import hu.unideb.inf.books_backend.repository.AuthorRepository;
import hu.unideb.inf.books_backend.service.common.CommonAuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Order(1)
@Slf4j
public class AuthorRunner
    implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRunner.class);
    private static final Faker FAKER = new Faker();

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        authorRepository.save(Author.builder()
                        .name("ADMINAUTHOR")
                        .email("adminauthor@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .username("admin")
                        .admin(true)
                .build());

        for (int i = 0; i < 50; i++) {
            final var name = FAKER.name();
            final var firstName = FAKER.name().firstName();
            final var lastName = FAKER.name().lastName();
            final var author = authorRepository.save(
                    Author.builder()
                            .name(firstName + " " + lastName)
                            .username(firstName.toLowerCase() + lastName.toLowerCase())
                            .password(passwordEncoder.encode(firstName.toLowerCase() + lastName.toLowerCase()))
                            .email(firstName.toLowerCase() + lastName.toLowerCase() + "@gmail.com")
                            .admin(false)
                            .build()
            );

            LOGGER.info("Author {} has been created", author);
        }
    }

}
