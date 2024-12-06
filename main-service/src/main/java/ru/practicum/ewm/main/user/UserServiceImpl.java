package ru.practicum.ewm.main.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.main.exception.NotFoundException;
import ru.practicum.ewm.main.user.dto.UserCreateDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAll(List<Long> ids, Integer from, Integer size) {
        if (ids == null) {
            return userRepository.findLimitOffset(from, size);
        }
        return userRepository.findByIdsLimitOffset(ids, from, size);
    }

    @Override
    public User create(UserCreateDto userCreateDto) {
        User user = UserMapper.toEntity(userCreateDto);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));

        userRepository.deleteById(userId);
    }

}
