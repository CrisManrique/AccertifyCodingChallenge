package org.accertify.handler;
import org.accertify.model.InputRequest;
import org.accertify.model.ResponseBody;
import org.accertify.model.TransformedRequest;
import org.accertify.repository.WordRepository;
import org.accertify.utility.WordChecker;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WordHandler {
    private final WordRepository wordRepository;
    private final WordChecker wordChecker;
    private static final String KEY_WORD = "word";
    private static final String KEY_SUB_WORDS = "sub_words";

    public WordHandler(WordRepository wordRepository, WordChecker wordChecker) {
        this.wordRepository = wordRepository;
        this.wordChecker = wordChecker;
    }

    public Mono<ResponseBody> getWordAndSubwords(InputRequest inputRequest){
        return Mono.just(inputRequest)
                .flatMap(request -> wordRepository.getWordAndSubwords(request.getWord()))
                        .switchIfEmpty(Mono.error(new Exception("Word is not present in the database")))
                .flatMap(this::convertDataToResponseBody);
    }

    public Mono<ResponseBody> getAllWords(){
        return
                wordRepository.getAllWords()
                .switchIfEmpty(Mono.error(new Exception("Word is not present in the database")))
                .flatMap(this::convertDataToResponseBody);
    }
    public Mono<ResponseBody> addWord(InputRequest inputRequest){
        return Mono.just(inputRequest)
                .flatMap(this::transformRequest)
                .flatMap(transformedRequest ->
                    wordRepository.addWord(transformedRequest.getWord(), transformedRequest.getSubWords()))
                .map(ResponseBody::new);
    }

    public Mono<ResponseBody> deleteWord(InputRequest inputRequest){
        return Mono.just(inputRequest)
                .flatMap(wordRepository::deleteWord)
                .map(i -> new ResponseBody("Successfully deleted record"));
    }

    public Mono<ResponseBody> findSubWords(InputRequest inputRequest){
        return Mono.just(inputRequest)
                .flatMap(this::transformRequest)
                .flatMap(this::convertDataToResponseBody)
                .map(recordMap -> new ResponseBody(recordMap, "Successfully found sub-words for " + inputRequest.getWord()));
    }

    private Mono<Map<String, List<String>>> convertDataToResponseBody(TransformedRequest transformedRequest){
        Map<String, List<String>> recordMap = new HashMap<>();
        recordMap.put(transformedRequest.getWord(), transformedRequest.getSubWords());
        return Mono.just(recordMap);
    }

    public Mono<ResponseBody> convertDataToResponseBody(List<Map<String, Object>> recordMap){
         Map<String, List<String>> responseMap = recordMap
                .stream()
                .collect(Collectors.toMap(s -> (String) s.get(KEY_WORD),
                        s -> {
                        List<Object> subWordList = Collections.singletonList(s.get(KEY_SUB_WORDS));
                           return subWordList.stream()
                                    .map(Object::toString)
                                    .map(word -> word.replaceAll("^\"|\"$", "")) //Remove extra quotes from each string
                                    .collect(Collectors.toList());
                    }));
         String successMessage = "Successfully retrieved words from database";
         return Mono.just(new ResponseBody(responseMap, successMessage));
    }

    private Mono<TransformedRequest> transformRequest(InputRequest inputRequest) {
        List<String> subWords;
        List<String> substrings = getAllSubstrings(inputRequest.getWord());

        subWords = substrings
                .parallelStream()
                .filter(wordChecker::isWord)
                .collect(Collectors.toList());
        return Mono.just(new TransformedRequest(inputRequest.getWord(), subWords));
    }

    private List<String> getAllSubstrings(String word){
        List<String> substrings = new ArrayList<>();
        int n = word.length();

        for (int start = 0; start < n; start++) {
            for (int end = start + 1; end <= n; end++) {
                String substring = word.substring(start, end);
                substrings.add(substring);
            }
        }
        return substrings;
    }
}
