package mate.academy.book.store.service.user;

import mate.academy.book.store.dto.user.request.UserRegistrationRequestDto;
import mate.academy.book.store.dto.user.response.UserResponseDto;
import mate.academy.book.store.exception.RegistrationException;
import mate.academy.book.store.model.User;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;

    User getAuthenticatedUser();

    User getById(Long id);
}
