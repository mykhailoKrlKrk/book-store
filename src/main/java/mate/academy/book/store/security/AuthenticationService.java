package mate.academy.book.store.security;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import mate.academy.book.store.dto.user.UserLoginRequestDto;
import mate.academy.book.store.dto.user.UserLoginResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        String token = jwtUtil.getToken(authentication.getName());
        return new UserLoginResponseDto(token);
    }
}
