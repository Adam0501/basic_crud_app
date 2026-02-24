package hu.unideb.inf.books_backend.service.common;

import hu.unideb.inf.books_backend.service.common.dto.CommonAuthResponse;
import lombok.NonNull;

import java.security.Principal;

public interface CommonAuthService {

    CommonAuthResponse getMe(@NonNull String username);

}
