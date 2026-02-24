package hu.unideb.inf.books_backend.service.admin;

import hu.unideb.inf.books_backend.service.admin.dto.AdminAuthor;
import hu.unideb.inf.books_backend.service.admin.dto.AdminAuthorRequest;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface AdminAuthorService {

    AdminAuthor getOne(@NonNull UUID authorId);

    List<AdminAuthor> getAll();

    AdminAuthor createOne(AdminAuthorRequest adminAuthorRequest);

    AdminAuthor updateOne(AdminAuthorRequest adminAuthorRequest);

    List<AdminAuthor> search(@NonNull String username);

    void deleteOne(@NonNull UUID authorId);
}
