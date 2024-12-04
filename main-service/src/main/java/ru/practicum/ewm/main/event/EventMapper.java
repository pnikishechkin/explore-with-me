package ru.practicum.ewm.main.event;

import ru.practicum.ewm.main.category.CategoryMapper;
import ru.practicum.ewm.main.event.dto.EventCreateDto;
import ru.practicum.ewm.main.event.dto.EventFullDto;
import ru.practicum.ewm.main.event.dto.EventShortDto;
import ru.practicum.ewm.main.event.dto.EventUserUpdateDto;
import ru.practicum.ewm.main.user.UserMapper;

public class EventMapper {
    public static Event toEntity(EventCreateDto eventCreateDto) {
        Event event = new Event();
        event.setAnnotation(eventCreateDto.getAnnotation());
        event.setDescription(eventCreateDto.getDescription());
        event.setAnnotation(eventCreateDto.getAnnotation());
        event.setRequestModeration(eventCreateDto.getRequestModeration());
        event.setParticipantLimit(eventCreateDto.getParticipantLimit());
        event.setEventDate(eventCreateDto.getEventDate());
        event.setTitle(eventCreateDto.getTitle());
        event.setPaid(eventCreateDto.getPaid());
        event.setLocation(eventCreateDto.getLocation());

        return event;
    }

    public static EventFullDto toDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setId(event.getId());

        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setAnnotation(event.getAnnotation());

        eventFullDto.setCategory(CategoryMapper.toDto(event.getCategory()));
        eventFullDto.setInitiator(UserMapper.toDto(event.getInitiator()));

        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());

        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setCreatedOn(event.getCreatedOn());
        eventFullDto.setPublishedOn(event.getPublishedDate());

        eventFullDto.setLocation(event.getLocation());

        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setState(event.getState());

        eventFullDto.setConfirmedRequests(0);
        eventFullDto.setViews(0);

        return eventFullDto;
    }

    public static EventShortDto toShortDto(Event event) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setId(event.getId());

        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setAnnotation(event.getAnnotation());

        eventShortDto.setCategory(CategoryMapper.toDto(event.getCategory()));
        eventShortDto.setInitiator(UserMapper.toDto(event.getInitiator()));

        eventShortDto.setEventDate(event.getEventDate());

        eventShortDto.setPaid(event.getPaid());

        eventShortDto.setConfirmedRequests(0);
        eventShortDto.setViews(0);

        return eventShortDto;
    }

    public static Event toEntity(EventUserUpdateDto eventUserUpdateDto, Event event) {


        return event;
    }
}
