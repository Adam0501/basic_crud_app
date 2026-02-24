package hu.unideb.inf.books_backend.web.common;

import hu.unideb.inf.books_backend.service.common.dto.CommonAuthResponse;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

public interface CommonAuthController {

    @GetMapping(value = "/api/me")
    CommonAuthResponse getMe(@NonNull Principal principal);

}
