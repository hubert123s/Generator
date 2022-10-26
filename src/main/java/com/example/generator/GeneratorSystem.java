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
    // A method that is called every 100 milliseconds. It checks if there is a generator that is waiting to be generated.
    // If there is, it sets the status to active, and then generates all the strings and saves them to a file.
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

    /**
     * It takes a set of strings and a generator parameters object, and writes the strings to a file
     *
     * @param stringSet The set of strings to be written to the file.
     * @param generatorParameters the parameters that the user has entered in the GUI.
     */
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
    /**
     * It reads a file with a given id and returns a list of strings
     *
     * @param id the id of the file to be read
     * @return List of Strings
     */
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
    /**
     * It generates a set of strings with the given parameters
     *
     * @param generatorParameters This is the object that contains all the parameters that the user has entered.
     * @return A set of strings.
     */
    public Set<String> generateAllString(GeneratorParameters generatorParameters) {
        HashSet<String> stringSet = new HashSet<>();
        List<String> distinctString = selectedCharacters(generatorParameters.getChars());
        while (stringSet.size() < generatorParameters.getNumbersOfString()) {
            String temp = generateString(distinctString, generatorParameters);
                stringSet.add(temp);
        }
        return stringSet;
    }

    /**
     * It generates a random string of random length, with random characters from a list of distinct characters
     *
     * @param distinctString A list of strings that are distinct from each other.
     * @param generatorParameters This is the object that contains the parameters for the generator.
     * @return A random string of characters.
     */
    public String generateString(List<String> distinctString, GeneratorParameters generatorParameters) {
        int length = random.nextInt(generatorParameters.getMin(), generatorParameters.getMax());
        int[] numbers = random.ints(0, generatorParameters.getMax()).limit(length).toArray();
        StringBuilder newString = new StringBuilder();
        for (int number : numbers) {
            newString.append(distinctString.get(number));
        }
        return newString.toString();
    }

    /**
     * "Return a list of distinct characters from the input string, but only if the character is a single character."
     *
     * @param chars a string of characters
     * @return A list of distinct characters that are one character long.
     */
    public List<String> selectedCharacters(String chars) {
        return Arrays.stream(chars.trim().split(","))
                .distinct()
                .filter(m -> m.length() == 1)
                .toList();
    }

    /**
     * Given a maximum number of characters and a character set, calculate the maximum number of possible combinations.
     *
     * @param max The maximum number of characters to generate.
     * @param charsSize The size of the character set you're using.
     * @return The maximum number of characters that can be generated.
     */
    public int calculateMaxCharsGenerated(int max, int charsSize) {
        return (int) Math.pow(charsSize, max);
    }
}
