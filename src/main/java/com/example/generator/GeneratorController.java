package com.example.generator;

import com.example.generator.model.GeneratorParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/generator")

class GeneratorController {
    private final GeneratorService generatorService;

    @PostMapping
    void saveGenerator(@RequestBody GeneratorParameters generatorParameters) {
        generatorService.saveFileAndGenerate(generatorParameters);
    }

    @GetMapping
    List<GeneratorParameters> getActiveGenerator() {
        return generatorService.findActiveGenerator();
    }

    @GetMapping("/{id}")
    List <String> getGeneratorByIdFromFile(@PathVariable Long id) {
        return generatorService.findFileById(id);
    }
}
