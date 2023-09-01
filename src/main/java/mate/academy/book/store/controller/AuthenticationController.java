package mate.academy.book.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.book.store.dto.user.UserLoginRequestDto;
import mate.academy.book.store.dto.user.UserLoginResponseDto;
import mate.academy.book.store.dto.user.UserRegistrationRequestDto;
import mate.academy.book.store.dto.user.UserResponseDto;
import mate.academy.book.store.security.AuthenticationService;
import mate.academy.book.store.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request) {
        return userService.register(request);
    }
}
