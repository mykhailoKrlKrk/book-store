package mate.academy.book.store.service;

import mate.academy.book.store.dto.user.UserRegistrationRequestDto;
import mate.academy.book.store.dto.user.UserResponseDto;
import mate.academy.book.store.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
}
