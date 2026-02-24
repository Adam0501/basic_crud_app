package hu.unideb.inf.books_backend.service.author.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.unideb.inf.books_backend.model.Author;
import hu.unideb.inf.books_backend.model.Book;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class AuthorBookResponse {
    private UUID id;
    private String title;
    private OffsetDateTime publishDate;
    @JsonIgnoreProperties("books")
    private Set<Author> authors;

    public static AuthorBookResponse of(@NonNull final Book book) {
        return AuthorBookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .publishDate(book.getPublishDate())
                .authors(book.getAuthors())
                .build();
    }
}
