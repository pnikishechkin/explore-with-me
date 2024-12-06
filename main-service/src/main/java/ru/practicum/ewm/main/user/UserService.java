package ru.practicum.ewm.main.user;

import ru.practicum.ewm.main.user.dto.UserCreateDto;

import java.util.List;

public interface UserService {
    List<User> getAll(List<Long> ids, Integer from, Integer size);

    User create(UserCreateDto userCreateDto);

    void delete(Long userId);
}
