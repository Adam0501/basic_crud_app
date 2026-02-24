package hu.unideb.inf.books_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Stream;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "AUTHORS")
public class Author implements UserDetails {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    @Column(unique = true)
    @NotNull
    private String username;
    @Column(unique = true)
    @NotNull
    private String email;

    @JsonIgnore
    @NotNull
    private String password;
    private boolean admin;

    @Builder.Default
    @ManyToMany(mappedBy = "authors")
    @JsonIgnoreProperties("authors")
    private Set<Book> books = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return isAdmin()
                ? Stream.of("ROLE_USER", "ROLE_ADMIN").map(SimpleGrantedAuthority::new).toList()
                : Stream.of("ROLE_USER").map(SimpleGrantedAuthority::new).toList();
    }
}
