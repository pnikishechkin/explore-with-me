package ru.practicum.ewm.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.comment.CommentService;
import ru.practicum.ewm.main.comment.dto.CommentFullDto;
import ru.practicum.ewm.main.comment.dto.CommentShortDto;
import ru.practicum.ewm.main.common.OffsetPageRequest;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommentControllerPublic {

    private final CommentService commentService;

    @GetMapping("/comments/{commentId}")
    public CommentFullDto getById(@PathVariable Long commentId) {
        return commentService.getById(commentId);
    }

    @GetMapping("/events/{eventId}/comments")
    public List<CommentShortDto> getByEventId(@PathVariable Long eventId,
                                              @RequestParam(defaultValue = "0") Integer from,
                                              @RequestParam(defaultValue = "10") Integer size) {
        OffsetPageRequest pr = new OffsetPageRequest(from, size, Sort.by("id"));
        return commentService.getByEventId(eventId, pr);
    }

}
