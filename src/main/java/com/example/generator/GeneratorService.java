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

    /**
     * The function checks if the min and max values are valid, if the selected characters are valid, and if the number of
     * strings is valid. If all of these are valid, the function saves the parameters to the database
     *
     * @param generatorParameters the object that contains all the parameters of the generator.
     */
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

    /**
     * Find all generators that are active.
     *
     * @return A list of GeneratorParameters objects
     */
    public List<GeneratorParameters> findActiveGenerator() {
        return generatorRepository.findAllByStatus(Status.ACTIVE);
    }

    /**
     * This function reads a file from the file system and returns a list of strings.
     *
     * @param id The id of the file to be read
     * @return A list of strings.
     */
    public List<String> findFileById(Long id) {
       return generatorSystem.readFile(id);
    }

}
