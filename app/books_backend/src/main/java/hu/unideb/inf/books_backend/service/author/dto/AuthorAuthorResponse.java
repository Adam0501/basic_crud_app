package hu.unideb.inf.books_backend.service.author.dto;

import hu.unideb.inf.books_backend.model.Author;
import hu.unideb.inf.books_backend.model.Book;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class AuthorAuthorResponse {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private Set<Book> books;

    public static AuthorAuthorResponse from(@NonNull final Author author) {
        return AuthorAuthorResponse.builder()
                .id(author.getId())
                .username(author.getUsername())
                .name(author.getName())
                .email(author.getEmail())
                .books(author.getBooks())
                .build();
    }
}
