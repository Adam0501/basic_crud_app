package hu.unideb.inf.books_backend.web.admin;

import hu.unideb.inf.books_backend.service.admin.AdminAuthorService;
import hu.unideb.inf.books_backend.service.admin.dto.AdminAuthor;
import hu.unideb.inf.books_backend.service.admin.dto.AdminAuthorRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class AdminAuthorControllerImpl implements AdminAuthorController {

    private final AdminAuthorService adminAuthorService;

    @Override
    public AdminAuthor getOne(@NonNull UUID authorId) {
        return adminAuthorService.getOne(authorId);
    }

    @Override
    public List<AdminAuthor> getAll() {
        return adminAuthorService.getAll();
    }

    @Override
    public AdminAuthor createOne(AdminAuthorRequest adminAuthorRequest) {
        return adminAuthorService.createOne(adminAuthorRequest);
    }

    @Override
    public AdminAuthor updateOne(AdminAuthorRequest adminAuthorRequest) {
        return adminAuthorService.updateOne(adminAuthorRequest);
    }

    @Override
    public List<AdminAuthor> searchAuthor(@NonNull String username) {
        return adminAuthorService.search(username);
    }

    @Override
    public void deleteOne(@NonNull UUID authorId) {
        adminAuthorService.deleteOne(authorId);
    }
}
