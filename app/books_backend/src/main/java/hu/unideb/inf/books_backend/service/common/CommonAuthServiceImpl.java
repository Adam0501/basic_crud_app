package hu.unideb.inf.books_backend.service.common;

import hu.unideb.inf.books_backend.repository.AuthorRepository;
import hu.unideb.inf.books_backend.service.common.dto.CommonAuthResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommonAuthServiceImpl implements CommonAuthService {

    private final AuthorRepository authorRepository;

    @Override
    public CommonAuthResponse getMe(@NonNull String username) {
        return authorRepository.findByUsername(username)
                .map(author -> CommonAuthResponse.builder()
                        .username(author.getUsername())
                        .password(author.getPassword())
                        .roles(author.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toSet()))
                        .build())
                .orElseThrow(() -> new IllegalArgumentException("This shouldn't throw"));
    }
}
