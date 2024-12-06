package ru.practicum.ewm.main.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.event.EventRepository;
import ru.practicum.ewm.main.event.EventState;
import ru.practicum.ewm.main.event.model.Event;
import ru.practicum.ewm.main.exception.ConflictException;
import ru.practicum.ewm.main.exception.NotFoundException;
import ru.practicum.ewm.main.request.dto.RequestDto;
import ru.practicum.ewm.main.user.User;
import ru.practicum.ewm.main.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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
    public RequestDto create(Long userId, Long eventId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Ошибка! Пользователя с заданным идентификатором не существует"));

        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Ошибка! События с заданным идентификатором не существует"));

        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Не может быть создан запрос на участие от автора события");
        }

        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Событие не опубликовано или отменено, создание заявки невозможно");
        }

        Integer countConfirmedRequest = requestRepository.getCountConfirmedRequestByEvent(eventId);

        log.info("Количество подтвержденных запросов на участие в событии: {}", countConfirmedRequest);

        if (event.getParticipantLimit() != 0 && countConfirmedRequest >= event.getParticipantLimit()) {
            throw new ConflictException("На событие не осталось свободных мест");
        }

        if (requestRepository.findByUserIdAndEventId(userId, eventId).isPresent()) {
            throw new ConflictException("Уже имеется запрос от данного пользователя на участие в данном событии");
        }

        Request request = new Request();
        request.setDate(LocalDateTime.now());
        request.setEvent(event);

        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            request.setStatus(RequestStatus.CONFIRMED);
        } else {
            request.setStatus(RequestStatus.PENDING);
        }
        request.setUser(user);

        return RequestMapper.toDto(requestRepository.save(request));
    }

    @Override
    public RequestDto cancel(Long userId, Long requestId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Ошибка! Пользователя с заданным идентификатором не существует"));

        Request request = requestRepository.findById(requestId).orElseThrow(() ->
                new NotFoundException("Ошибка! Запроса с заданным идентификатором не существует"));

        request.setStatus(RequestStatus.CANCELED);

        return RequestMapper.toDto(requestRepository.save(request));
    }

}
