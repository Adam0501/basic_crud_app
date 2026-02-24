package hu.unideb.inf.books_backend.service.admin.dto;

import hu.unideb.inf.books_backend.model.Author;
import hu.unideb.inf.books_backend.model.Book;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@Builder
public class AdminAuthor {
    UUID id;
    String name;
    String username;
    String email;
    boolean admin;
    Set<Book> books;

    public static AdminAuthor from(@NonNull final Author author) {
        return AdminAuthor.builder()
                .id(author.getId())
                .name(author.getName())
                .username(author.getUsername())
                .email(author.getEmail())
                .admin(author.isAdmin())
                .books(author.getBooks())
                .build();
    }
}
