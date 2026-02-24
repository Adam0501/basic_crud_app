package hu.unideb.inf.books_backend.service.common.dto;


import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CommonAuthResponse {
    private String username;
    private String password;
    private Set<String> roles;
}
