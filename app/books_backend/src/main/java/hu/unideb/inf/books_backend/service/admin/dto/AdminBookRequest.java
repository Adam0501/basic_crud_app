package hu.unideb.inf.books_backend.service.admin.dto;

import hu.unideb.inf.books_backend.model.Book;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Data
public class AdminBookRequest {
    private String bookId;
    private String title;
    private OffsetDateTime publishDate;
    private List<String> authorUsernames;
}
