package hu.unideb.inf.books_backend.service.author.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorAuthorRequest {
    private String username;
    private String email;
    private String password;
}

