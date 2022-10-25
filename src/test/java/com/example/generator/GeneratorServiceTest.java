package com.example.generator;

import com.example.generator.exception.WrongMinAndMaxValueException;
import com.example.generator.model.GeneratorParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class GeneratorServiceTest {
    @Autowired
    GeneratorService generatorService;
    @Autowired
    GeneratorRepository generatorRepository;
    @Autowired
    GeneratorSystem generatorSystem;

    @Test
    void shouldSaveFileAndGenerate() {
        GeneratorParameters generatorParameters = new GeneratorParameters();
        generatorParameters.setId(1L);
        generatorParameters.setMin(10);
        generatorParameters.setMax(5);
        generatorParameters.setNumbersOfString(5L);
        generatorParameters.setChars("a,b,c");
        Assertions.assertThrows(WrongMinAndMaxValueException.class
                ,()->generatorService.saveFileAndGenerate(generatorParameters));
    }


}