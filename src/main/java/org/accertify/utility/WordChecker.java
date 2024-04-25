package org.accertify.utility;
import org.accertify.exceptions.AccertifyRuntimeException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@Component
public class WordChecker {
    private Set<String> wordSet;
    @PostConstruct
    private void initialize()  {
        String filePath = "src/main/resources/english.txt";
        wordSet = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordSet.add(line.trim()); // Add each line to the HashSet, optionally trim to remove leading and trailing whitespace
            }
        } catch (IOException e) {
            throw new AccertifyRuntimeException("Initialization of word set went wrong " , e);
        }
    }

    public boolean isWord(String word) {
        return wordSet.contains(word);
    }
}
