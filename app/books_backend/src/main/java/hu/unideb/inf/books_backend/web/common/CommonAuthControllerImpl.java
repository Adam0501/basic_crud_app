package hu.unideb.inf.books_backend.web.common;

import hu.unideb.inf.books_backend.service.common.CommonAuthService;
import hu.unideb.inf.books_backend.service.common.dto.CommonAuthResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class CommonAuthControllerImpl implements CommonAuthController {

    private final CommonAuthService commonAuthService;

    @Override
    public CommonAuthResponse getMe(@NonNull Principal principal) {
        return commonAuthService.getMe(principal.getName());
    }

}
