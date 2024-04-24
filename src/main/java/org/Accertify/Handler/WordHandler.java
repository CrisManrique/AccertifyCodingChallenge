package org.Accertify.Handler;
import org.Accertify.Model.InputRequest;
import org.Accertify.Model.ResponseBody;
import org.Accertify.Repository.WordRepository;
import org.Accertify.Utility.Utilities;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class WordHandler {
    private final WordRepository wordRepository;
    private static final String KEY_WORD = "word";
    private static final String KEY_SUB_WORDS = "sub_words";
    public WordHandler(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Mono<ResponseBody> getWords(InputRequest inputRequest){
        return Mono.just(inputRequest)
                .flatMap(Utilities::validateRequest)
                .flatMap(validRequest -> wordRepository.getSubwords(validRequest.getWord())
                        .switchIfEmpty(Mono.error(new RuntimeException("Word is not present in the database"))))
                .flatMap(this::convertDataToResponseBody);
    }

    public Mono<ResponseBody> convertDataToResponseBody(List<Map<String, Object>> recordMap){
        String word = recordMap.get(0).get(KEY_WORD).toString();
        List<Object> subWordsObject = Collections.singletonList(recordMap.get(0).get(KEY_SUB_WORDS));
        // Convert List<Object> to List<String> explicitly
        List<String> subWordsList = subWordsObject.stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        Map<String, List<String>> subwordsMap = new HashMap<>();
        subwordsMap.put(word, subWordsList);
        return Mono.just(new ResponseBody(subwordsMap));
    }
}
