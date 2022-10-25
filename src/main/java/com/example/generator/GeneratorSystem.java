package com.example.generator;

import com.example.generator.exception.FileNotFoundException;
import com.example.generator.model.GeneratorParameters;
import com.example.generator.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@RequiredArgsConstructor
@Component
@EnableAsync
@EnableScheduling
public class GeneratorSystem {

    private final Random random = new Random();
    private final GeneratorRepository generatorRepository;

    @Async
    @Scheduled(fixedRate = 100)
    public void generateAll() {
        GeneratorParameters generatorParameters = generatorRepository.findFirstByStatus(Status.WAITING).orElse(null);
        if (generatorParameters != null) {
            generatorParameters.setStatus(Status.ACTIVE);
            generatorRepository.saveAndFlush(generatorParameters);
            Set<String> stringSet = generateAllString(generatorParameters);
            saveFile(stringSet, generatorParameters);
            generatorParameters.setStatus(Status.INACTIVE);
            generatorRepository.saveAndFlush(generatorParameters);
        }
    }

    public void saveFile(Set<String> stringSet, GeneratorParameters generatorParameters) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("id" + generatorParameters.getId() + ".txt", true))) {
            for (String singleString : stringSet) {
                bufferedWriter.write(singleString);
                bufferedWriter.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<String> readFile(Long id) {
        List<String> listToShow =new ArrayList<>();
        if (generatorRepository.existsById(id)) {
            try(BufferedReader reader =
                        new BufferedReader(new FileReader("id" + id + ".txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                listToShow.add(line);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new FileNotFoundException(id);
        }
        return listToShow;
    }
    public Set<String> generateAllString(GeneratorParameters generatorParameters) {
        HashSet<String> stringSet = new HashSet<>();
        List<String> distinctString = selectedCharacters(generatorParameters.getChars());
        while (stringSet.size() < generatorParameters.getNumbersOfString()) {
            String temp = generateString(distinctString, generatorParameters);
                stringSet.add(temp);
        }
        return stringSet;
    }

    public String generateString(List<String> distinctString, GeneratorParameters generatorParameters) {
        int length = random.nextInt(generatorParameters.getMin(), generatorParameters.getMax());
        int[] numbers = random.ints(0, generatorParameters.getMax()).distinct().limit(length).toArray();
        StringBuilder newString = new StringBuilder();
        for (int number : numbers) {
            newString.append(distinctString.get(number));
        }
        return newString.toString();
    }

    public List<String> selectedCharacters(String chars) {
        return Arrays.stream(chars.trim().split(","))
                .distinct()
                .filter(m -> m.length() == 1)
                .toList();
    }

    public int calculateMaxCharsGenerated(int max, int charsSize) {
        return (int) Math.pow(charsSize, max);
    }
}
