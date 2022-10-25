package com.example.generator;

import com.example.generator.exception.WrongMinAndMaxValueException;
import com.example.generator.model.GeneratorParameters;
import com.example.generator.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
//@AutoConfigureMockMvc
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