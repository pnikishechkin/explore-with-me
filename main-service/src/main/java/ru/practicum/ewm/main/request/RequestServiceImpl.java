package ru.practicum.ewm.main.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.event.Event;
import ru.practicum.ewm.main.event.EventRepository;
import ru.practicum.ewm.main.exception.NotFoundException;
import ru.practicum.ewm.main.request.dto.RequestDto;
import ru.practicum.ewm.main.user.User;
import ru.practicum.ewm.main.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public List<RequestDto> getRequestsByUser(Long userId) {
        List<Request> res = requestRepository.findByUserId(userId);
        return res.stream().map(RequestMapper::toDto).toList();
    }

    @Override
    public RequestDto post(Long userId, Long eventId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Ошибка! Пользователя с заданным идентификатором не существует"));

        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Ошибка! События с заданным идентификатором не существует"));

        // TODO проверка, что событие не того же пользователя

        Request request = new Request();
        request.setDate(LocalDateTime.now());
        request.setEvent(event);
        request.setStatus(RequestStatus.PENDING);
        request.setUser(user);

        return RequestMapper.toDto(requestRepository.save(request));
    }

    @Override
    public RequestDto cancel(Long userId, Long requestId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Ошибка! Пользователя с заданным идентификатором не существует"));

        Request request = requestRepository.findById(requestId).orElseThrow(() ->
                new NotFoundException("Ошибка! Запроса с заданным идентификатором не существует"));

        // TODO проверка, что событие того же пользователя

        request.setStatus(RequestStatus.CANCELED);

        return RequestMapper.toDto(requestRepository.save(request));
    }

}
