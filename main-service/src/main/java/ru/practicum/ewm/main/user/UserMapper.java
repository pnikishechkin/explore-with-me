package ru.practicum.ewm.main.user;

import ru.practicum.ewm.main.user.dto.UserCreateDto;
import ru.practicum.ewm.main.user.dto.UserShortDto;

public class UserMapper {
    public static User toEntity(UserCreateDto userCreateDto) {
        User user = new User();
        user.setName(userCreateDto.getName());
        user.setEmail(userCreateDto.getEmail());
        return user;
    }

    public static UserShortDto toDto(User user) {
        UserShortDto userShortDto = new UserShortDto();
        userShortDto.setId(user.getId());
        userShortDto.setName(user.getName());
        return userShortDto;
    }
}
