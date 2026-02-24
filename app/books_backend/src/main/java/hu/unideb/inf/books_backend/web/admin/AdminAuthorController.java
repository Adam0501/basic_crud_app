package hu.unideb.inf.books_backend.web.admin;

import hu.unideb.inf.books_backend.service.admin.dto.AdminAuthor;
import hu.unideb.inf.books_backend.service.admin.dto.AdminAuthorRequest;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public interface AdminAuthorController {

    @GetMapping(value = "/api/admin/author/{authorId}")
    AdminAuthor getOne(@NonNull @PathVariable UUID authorId);

    @GetMapping(value = "/api/admin/author")
    List<AdminAuthor> getAll();

    @PostMapping(value = "/api/admin/author")
    AdminAuthor createOne(@RequestBody AdminAuthorRequest adminAuthorRequest);

    @PutMapping(value = "/api/admin/author")
    AdminAuthor updateOne(@RequestBody AdminAuthorRequest adminAuthorRequest);

    @GetMapping(value = "/search/author")
    List<AdminAuthor> searchAuthor(@NonNull @RequestParam String username);

    @DeleteMapping(value = "/api/admin/author/{authorId}")
    void deleteOne(@NonNull @PathVariable UUID authorId);
}
