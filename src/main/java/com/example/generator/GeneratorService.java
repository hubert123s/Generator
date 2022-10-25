package com.example.generator;

import com.example.generator.exception.NotFoundValidCharactersException;
import com.example.generator.exception.TooManyStringsException;
import com.example.generator.exception.WrongMinAndMaxValueException;
import com.example.generator.model.GeneratorParameters;
import com.example.generator.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
class GeneratorService {
    private final GeneratorRepository generatorRepository;
    private final GeneratorSystem generatorSystem;

    public void saveFileAndGenerate(GeneratorParameters generatorParameters) {
        int charsSize = generatorSystem.selectedCharacters(generatorParameters.getChars()).size();
        if (generatorParameters.getMin() > generatorParameters.getMax()) {
            throw new WrongMinAndMaxValueException(generatorParameters.getMin(),generatorParameters.getMax());
        } else if (charsSize == 0) {
            throw new NotFoundValidCharactersException(generatorParameters.getChars());
        } else if (generatorParameters.getNumbersOfString() >= generatorSystem.calculateMaxCharsGenerated(generatorParameters.getMax(), charsSize)) {
            throw new TooManyStringsException();
        } else {
            generatorRepository.save(generatorParameters);
        }
    }

    public List<GeneratorParameters> findActiveGenerator() {
        return generatorRepository.findAllByStatus(Status.ACTIVE);
    }

    public List<String> findFileById(Long id) {
       return generatorSystem.readFile(id);
    }

}
