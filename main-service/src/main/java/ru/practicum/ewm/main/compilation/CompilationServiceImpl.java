package ru.practicum.ewm.main.compilation;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.main.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main.compilation.dto.UpdateCompilationDto;
import ru.practicum.ewm.main.event.EventRepository;
import ru.practicum.ewm.main.event.model.Event;
import ru.practicum.ewm.main.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class CompilationServiceImpl implements CompilationService {
    private static final Logger log = LoggerFactory.getLogger(CompilationServiceImpl.class);
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.toEntity(newCompilationDto);

        if (newCompilationDto.getEvents() != null) {
            List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
            if (events.size() != newCompilationDto.getEvents().size()) {
                throw new NotFoundException("Ошибка! События с заданным идентификатором не существует");
            }
            compilation.setEvents(events);
        } else {
            compilation.setEvents(List.of());
        }

        Compilation resCompilation = compilationRepository.save(compilation);
        return CompilationMapper.toDto(resCompilation);
    }

    @Override
    public void delete(Long compId) {
        compilationRepository.findById(compId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Подборки с заданным идентификатором не существует"));

        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto update(UpdateCompilationDto updateCompilationDto) {

        Compilation compilation =
                compilationRepository.findById(updateCompilationDto.getId()).orElseThrow(() -> new NotFoundException(
                        "Ошибка! Подборки с заданным идентификатором не существует"));

        if (updateCompilationDto.getPinned() != null) {
            compilation.setPinned(updateCompilationDto.getPinned());
        }
        if (updateCompilationDto.getTitle() != null) {
            compilation.setTitle(updateCompilationDto.getTitle());
        }
        if (updateCompilationDto.getEvents() != null) {
            List<Event> events = eventRepository.findAllById(updateCompilationDto.getEvents());
            if (events.size() != updateCompilationDto.getEvents().size()) {
                throw new NotFoundException("Ошибка! События с заданным идентификатором не существует");
            }
            compilation.setEvents(events);
        }

        Compilation resCompilation = compilationRepository.save(compilation);
        return CompilationMapper.toDto(resCompilation);
    }

    @Override
    public CompilationDto getById(Long compId) {
        Compilation compilation =
                compilationRepository.findById(compId).orElseThrow(() -> new NotFoundException(
                        "Ошибка! Подборки с заданным идентификатором не существует"));
        log.info(compilation.toString());
        return CompilationMapper.toDto(compilation);
    }

    @Override
    public List<CompilationDto> get(Boolean pinned, Integer from, Integer size) {

        List<Compilation> compilations;

        if (pinned != null) {
            log.info("Поиск подборок с учетом pinned");
            compilations = compilationRepository.findByPinnedFromSize(pinned, from, size);
        } else {
            log.info("Поиск подборок без учета pinned");
            compilations = compilationRepository.findByFromSize(from, size);
        }

        return compilations.stream().map(CompilationMapper::toDto).toList();

    }
}
