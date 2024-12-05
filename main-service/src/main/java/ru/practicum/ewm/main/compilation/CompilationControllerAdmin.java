package ru.practicum.ewm.main.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main.compilation.dto.UpdateCompilationDto;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CompilationControllerAdmin {
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto postCompilation(@RequestBody @Validated NewCompilationDto newCompilationDto) {
        return compilationService.create(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        compilationService.delete(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto patchCompilation(@PathVariable Long compId,
                                           @RequestBody @Validated UpdateCompilationDto updateCompilationDto) {
        updateCompilationDto.setId(compId);
        return compilationService.update(updateCompilationDto);
    }
}
