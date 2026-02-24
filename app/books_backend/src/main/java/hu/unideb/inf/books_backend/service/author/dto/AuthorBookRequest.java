package hu.unideb.inf.books_backend.service.author.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Builder
public class AuthorBookRequest {
    private String title;
    private OffsetDateTime publishDate;
    private Set<String> authorUsernames;
}
