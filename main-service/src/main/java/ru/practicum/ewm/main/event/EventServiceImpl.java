package ru.practicum.ewm.main.event;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.main.category.Category;
import ru.practicum.ewm.main.category.CategoryRepository;
import ru.practicum.ewm.main.event.dto.*;
import ru.practicum.ewm.main.exception.NotFoundException;
import ru.practicum.ewm.main.request.*;
import ru.practicum.ewm.main.request.dto.RequestDto;
import ru.practicum.ewm.main.request.dto.RequestsStatusUpdateDto;
import ru.practicum.ewm.main.request.dto.RequestsStatusUpdateResultDto;
import ru.practicum.ewm.main.user.User;
import ru.practicum.ewm.main.user.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class EventServiceImpl implements EventService {

    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final RequestServiceImpl requestServiceImpl;

    @Override
    public EventFullDto post(EventCreateDto eventCreateDto) {

        User user = userRepository.findById(eventCreateDto.getInitiatorId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));

        Category category = categoryRepository.findById(eventCreateDto.getCategory()).orElseThrow(() -> new NotFoundException(
                "Ошибка! Категории с заданным идентификатором не существует"));

        Event event = EventMapper.toEntity(eventCreateDto);
        event.setState(EventState.PENDING);
        event.setCategory(category);
        event.setInitiator(user);
        event.setCreatedOn(LocalDateTime.now());

        Event newEvent = eventRepository.save(event);
        EventFullDto eventFullDto = EventMapper.toDto(newEvent);

        return eventFullDto;
    }

    @Override
    public List<EventShortDto> getEventsByUser(Long userId, Integer from, Integer size) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));

        List<Event> res = eventRepository.findByInitiatorIdFromSize(userId, from, size);
        return res.stream().map(EventMapper::toShortDto).toList();
    }

    @Override
    public EventFullDto getEventByUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(
                "Ошибка! События с заданным идентификатором не существует"));

        if (event.getInitiator().getId().equals(user.getId())) {
            // TODO ошибка: событие другого пользователя

        }

        EventFullDto eventFullDto = EventMapper.toDto(event);
        eventFullDto.setConfirmedRequests(requestRepository.getCountConfirmedRequestByEvent(eventId));

        return eventFullDto;
    }

    @Override
    public EventFullDto patch(Long userId, EventUserUpdateDto eventUserUpdateDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));

        Event event = eventRepository.findById(eventUserUpdateDto.getId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! События с заданным идентификатором не существует"));

        if (event.getInitiator().getId().equals(user.getId())) {
            // TODO событие другого пользователя
        }

        if (eventUserUpdateDto.getAnnotation() != null) {
            event.setAnnotation(eventUserUpdateDto.getAnnotation());
        }
        if (eventUserUpdateDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(eventUserUpdateDto.getCategoryId()).orElseThrow(() -> new NotFoundException(
                    "Ошибка! Категории с заданным идентификатором не существует"));
            event.setCategory(category);
        }
        if (eventUserUpdateDto.getDescription() != null) {
            event.setDescription(eventUserUpdateDto.getDescription());
        }
        if (eventUserUpdateDto.getEventDate() != null) {
            event.setEventDate(eventUserUpdateDto.getEventDate());
        }
        if (eventUserUpdateDto.getLocation() != null) {
            event.setLocation(eventUserUpdateDto.getLocation());
        }
        if (eventUserUpdateDto.getPaid() != null) {
            event.setPaid(eventUserUpdateDto.getPaid());
        }
        if (eventUserUpdateDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventUserUpdateDto.getParticipantLimit());
        }
        if (eventUserUpdateDto.getRequestModeration() != null) {
            event.setRequestModeration(eventUserUpdateDto.getRequestModeration());
        }
        if (eventUserUpdateDto.getTitle() != null) {
            event.setTitle(eventUserUpdateDto.getTitle());
        }
        if (eventUserUpdateDto.getStateAction() == EventStateAction.CANCEL_REVIEW) {
            event.setState(EventState.CANCELED);
        } else if (eventUserUpdateDto.getStateAction() == EventStateAction.SEND_TO_REVIEW) {
            event.setState(EventState.PENDING);
        }

        Event updEvent = eventRepository.save(event);
        return EventMapper.toDto(updEvent);
    }

    @Override
    public EventFullDto patchByAdmin(EventAdminUpdateDto eventAdminUpdateDto) {

        Event event = eventRepository.findById(eventAdminUpdateDto.getId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! События с заданным идентификатором не существует"));

        if (eventAdminUpdateDto.getAnnotation() != null) {
            event.setAnnotation(eventAdminUpdateDto.getAnnotation());
        }
        if (eventAdminUpdateDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(eventAdminUpdateDto.getCategoryId()).orElseThrow(() -> new NotFoundException(
                    "Ошибка! Категории с заданным идентификатором не существует"));
            event.setCategory(category);
        }
        if (eventAdminUpdateDto.getDescription() != null) {
            event.setDescription(eventAdminUpdateDto.getDescription());
        }
        if (eventAdminUpdateDto.getEventDate() != null) {
            event.setEventDate(eventAdminUpdateDto.getEventDate());
        }
        if (eventAdminUpdateDto.getLocation() != null) {
            event.setLocation(eventAdminUpdateDto.getLocation());
        }
        if (eventAdminUpdateDto.getPaid() != null) {
            event.setPaid(eventAdminUpdateDto.getPaid());
        }
        if (eventAdminUpdateDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventAdminUpdateDto.getParticipantLimit());
        }
        if (eventAdminUpdateDto.getRequestModeration() != null) {
            event.setRequestModeration(eventAdminUpdateDto.getRequestModeration());
        }
        if (eventAdminUpdateDto.getTitle() != null) {
            event.setTitle(eventAdminUpdateDto.getTitle());
        }
        if (eventAdminUpdateDto.getStateAction() == EventAdminStateAction.PUBLISH_EVENT) {
            event.setState(EventState.PUBLISHED);
        } else if (eventAdminUpdateDto.getStateAction() == EventAdminStateAction.REJECT_EVENT) {
            event.setState(EventState.CANCELED);
        }

        Event updEvent = eventRepository.save(event);
        log.info("updEvent.getState() = " + updEvent.getState());
        return EventMapper.toDto(updEvent);
    }

    @Override
    public List<EventShortDto> getEventsByParams(EventParams eventParams) {

        List<Event> res = new ArrayList<>();

        LocalDateTime start = eventParams.getRangeStart() == null ? LocalDateTime.now() :
                LocalDateTime.parse(eventParams.getRangeStart(), DateTimeFormatter.ofPattern("yyyy-MM-dd " +
                        "HH:mm:ss"));
        LocalDateTime end = eventParams.getRangeEnd() != null ? end = LocalDateTime.parse(eventParams.getRangeEnd(), DateTimeFormatter.ofPattern("yyyy-MM-dd " +
                "HH:mm:ss")) : null;

        if (end != null && end.isBefore(start)) {
            throw new ValidationException("Дата окончания не может быть раньше начала");
        }

        if (eventParams.getCategories() == null) {
            eventParams.setCategories(List.of());
        }
        log.info("Поиск события, параметры: " + eventParams);
        EventSort eventSort;

        if (eventParams.getSort() != null) {
            eventSort = EventSort.valueOf(eventParams.getSort());
            if (eventSort.equals(EventSort.EVENT_DATE)) {
                res = eventRepository.findPublic(
                        eventParams.getText(),
                        start,
                        end,
                        eventParams.getCategories(),
                        eventParams.getPaid(),
                        eventParams.getOnlyAvailable(),
                        eventParams.getFrom(),
                        eventParams.getSize());
            }
        } else {
            res = eventRepository.findPublicNoSort(
                    eventParams.getText(),
                    start,
                    end,
                    eventParams.getCategories(),
                    eventParams.getPaid(),
                    eventParams.getOnlyAvailable(),
                    eventParams.getFrom(),
                    eventParams.getSize());
        }

        log.info(eventParams.getSort());

            /*
            if (eventParams.getText() == null) {
                // Не учитываем текст
                if (eventParams.getCategories() == null) {
                    // Не учитываем категории
                    if (eventParams.getRangeEnd() == null) {
                        // Не учитываем текст, категории и дату окончания
                        log.info("Поиск события: Не учитываем текст, категории и дату окончания");
                        res = eventRepository.findByStartPaidAvailableFromSize(
                                start,
                                paidValues,
                                onlyAvailableValues,
                                eventParams.getFrom(),
                                eventParams.getSize());
                    } else {
                        // Учитываем дату окончания. Не учитываем текст и категории.
                        log.info("Поиск события: Учитываем дату окончания. Не учитываем текст и категории.");
                        res = eventRepository.findByStartEndPaidAvailableFromSize(
                                start,
                                end,
                                paidValues,
                                onlyAvailableValues,
                                eventParams.getFrom(),
                                eventParams.getSize());
                    }
                } else {
                    // Учитываем категории
                    if (eventParams.getRangeEnd() == null) {
                        // Учитываем категории. Не учитываем текст и дату окончания.
                        log.info("Поиск события: Учитываем категории. Не учитываем текст и дату окончания.");
                        res = eventRepository.findByCategoriesStartPaidAvailableFromSize(
                                start,
                                eventParams.getCategories(),
                                paidValues,
                                onlyAvailableValues,
                                eventParams.getFrom(),
                                eventParams.getSize());
                    } else {
                        // Учитываем категории и дату окончания. Не учитываем текст.
                        log.info("Поиск события: Учитываем категории и дату окончания. Не учитываем текст.");
                        res = eventRepository.findByStartEndCategoriesPaidAvailableFromSize(
                                start,
                                end,
                                eventParams.getCategories(),
                                paidValues,
                                onlyAvailableValues,
                                eventParams.getFrom(),
                                eventParams.getSize());
                    }
                }
            } else {
                // Учитываем текст
                // Не учитываем категории
                if (eventParams.getCategories() == null) {
                    // Не учитываем дату окончания
                    if (eventParams.getRangeEnd() == null) {
                        // Учитываем текст. Не учитываем категории и дату окончания
                        log.info("Поиск события: Учитываем текст. Не учитываем категории и дату окончания.");
                        res = eventRepository.findByTextStartPaidAvailableFromSize(
                                eventParams.getText(),
                                start,
                                paidValues,
                                onlyAvailableValues,
                                eventParams.getFrom(),
                                eventParams.getSize());
                    } else {
                        // Учитываем текст и дату окончания. Не учитываем категории
                        log.info("Поиск события: Учитываем текст и дату окончания. Не учитываем категории");
                        res = eventRepository.findByTextStartEndPaidAvailableFromSize(
                                eventParams.getText(),
                                start,
                                end,
                                paidValues,
                                onlyAvailableValues,
                                eventParams.getFrom(),
                                eventParams.getSize());
                    }
                } else {
                    // Учитываем категории
                    if (eventParams.getRangeEnd() == null) {
                        // Учитываем текст и категории. Не учитываем дату окончания
                        log.info("Поиск события: Учитываем текст и категории. Не учитываем дату окончания");
                        res = eventRepository.findByTextStartCategoryPaidAvailableFromSize(
                                eventParams.getText(),
                                start,
                                eventParams.getCategories(),
                                paidValues,
                                onlyAvailableValues,
                                eventParams.getFrom(),
                                eventParams.getSize());
                    } else {
                        // Учитываем текст, категории и дату окончания
                        log.info("Поиск события: Учитываем текст, категории и дату окончания");
                        res = eventRepository.findByTextStartEndCategoryPaidAvailableFromSize(
                                eventParams.getText(),
                                start,
                                end,
                                eventParams.getCategories(),
                                paidValues,
                                onlyAvailableValues,
                                eventParams.getFrom(),
                                eventParams.getSize());
                    }
                }
            }
             */
//   else{
        // TODO Сортировка по количеству запросов:
        //  Вернуть из БД все результаты
        //  Получить статистику, склеить и отсортировать результаты, отсеять лишние (from, size)
//        }

        // TODO Сохранить запрос в сервисе статистики

        return res != null ? res.stream().map(EventMapper::toShortDto).toList() : null;
    }

    @Override
    public EventFullDto getPublicEventById(Long eventId) {
        Event event =
                eventRepository.findByIdAndState(eventId,
                        EventState.PUBLISHED).orElseThrow(() -> new NotFoundException(
                        "Ошибка! События с заданными параметрами не существует или оно еще не опубликовано"));
        EventFullDto eventFullDto = EventMapper.toDto(event);
        eventFullDto.setConfirmedRequests(requestRepository.getCountConfirmedRequestByEvent(eventId));
        return eventFullDto;
    }

    @Override
    public List<RequestDto> getRequestsByEvent(Long userId, Long eventId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Категории с заданным идентификатором не существует"));

        if (event.getInitiator().getId().equals(user.getId())) {
            // TODO ошибка: событие другого пользователя
        }

        List<Request> requests = requestRepository.findByEventId(eventId);
        return requests.stream().map(RequestMapper::toDto).toList();
    }

    @Override
    public RequestsStatusUpdateResultDto patchRequestsStatus(Long userId, Long eventId,
                                                             RequestsStatusUpdateDto requestsStatusUpdateDto) {
        List<Request> requests = requestRepository.findAllById(requestsStatusUpdateDto.getRequestIds());

        if (requests.size() != requestsStatusUpdateDto.getRequestIds().size()) {
            // TODO Не все запросы с указанными идентификаторами найдены
        }

        requests.forEach(r -> r.setStatus(requestsStatusUpdateDto.getStatus()));

        List<Request> res = requestRepository.saveAll(requests);

        List<RequestDto> resDto = res.stream().map(RequestMapper::toDto).toList();
        RequestsStatusUpdateResultDto result = new RequestsStatusUpdateResultDto();
        if (requestsStatusUpdateDto.getStatus().equals(RequestStatus.CONFIRMED)) {
            result.setConfirmedRequests(resDto);
        } else {
            result.setRejectedRequests(resDto);
        }

        return result;
    }

    public List<EventFullDto> getEventsByAdmin(EventAdminParams eap) {

        List<Integer> statesNum = List.of();
        LocalDateTime start = null;
        LocalDateTime end = null;

        if (eap.getStates() != null) {
            statesNum = eap.getStates().stream().map(EventState::valueOf).map(Enum::ordinal).toList();
        }
        if (eap.getRangeStart() != null) {
            start = LocalDateTime.parse(eap.getRangeStart(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        if (eap.getRangeEnd() != null) {
            end = LocalDateTime.parse(eap.getRangeEnd(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        if (eap.getUsersIds() == null) {
            eap.setUsersIds(List.of());
        }
        if (eap.getCategoriesIds() == null) {
            eap.setCategoriesIds(List.of());
        }

        List<Event> events = eventRepository.findByAdmin(eap.getUsersIds(), statesNum, eap.getCategoriesIds(),
                start, end, eap.getFrom(), eap.getSize());

        // TODO количество подтвержденных запросов

        // TODO количество просмотров

        return events.stream().map(EventMapper::toDto).toList();
    }
}