package org.Accertify.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class WordRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_WORD_SUBWORDS = "SELECT * FROM subwords WHERE word=?";
    public WordRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Mono<List<Map<String, Object>>> getSubwords(String word) {
        return Mono.fromCallable(() -> jdbcTemplate.queryForList(GET_WORD_SUBWORDS, word))
                .flatMap(words -> {
                    if(words.isEmpty()){
                        return Mono.empty();
                    } else {
                        return Mono.just(words);
                    }
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
