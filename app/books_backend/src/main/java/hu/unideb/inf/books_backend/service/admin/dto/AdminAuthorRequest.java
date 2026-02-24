package hu.unideb.inf.books_backend.service.admin.dto;

import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
public class AdminAuthorRequest {
    private UUID id;
    private String username;
    private String name;
    private String email;
    private boolean admin;
    private List<UUID> bookIDList;
}
