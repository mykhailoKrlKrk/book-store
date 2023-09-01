package mate.academy.book.store.mapper;

import mate.academy.book.store.config.MapperConfig;
import mate.academy.book.store.dto.user.UserRegistrationRequestDto;
import mate.academy.book.store.dto.user.UserResponseDto;
import mate.academy.book.store.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);
    User toModel(UserRegistrationRequestDto requestDto);
}
