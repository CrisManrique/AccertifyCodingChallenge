package org.accertify.repository;

import org.accertify.exceptions.AccertifyRuntimeException;
import org.accertify.model.InputRequest;
import org.accertify.model.PaginationRequest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class WordRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_WORD_SUBWORDS = "SELECT * FROM subwords WHERE word=?";
    private static final String GET_ALL_WORDS = "SELECT * FROM subwords";
    private static final String DELETE_WORD = "DELETE FROM subwords WHERE word=?";
    private static final String ADD_WORD = "INSERT INTO subwords (word, sub_words) VALUES (?, ?)";

    public WordRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Mono<List<Map<String, Object>>> getWordAndSubwords(String word) {
        return Mono.fromCallable(() -> jdbcTemplate.queryForList(GET_WORD_SUBWORDS, word))
                .flatMap(words -> {
                    if(words.isEmpty()) {
                        return Mono.defer(Mono::empty);
                    } else {
                        return Mono.defer(() -> Mono.just(words));
                    }
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<List<Map<String, Object>>> getAllWords(PaginationRequest paginationRequest) {
        return Mono.fromCallable(() -> jdbcTemplate.queryForList(GET_ALL_WORDS))
                .flatMap(words -> {
                    if(words.isEmpty()) {
                        return Mono.defer(Mono::empty);
                    } else {
                        return Mono.defer(() -> (paginationRequest != null) ? Mono.just(reduceListSize(words, paginationRequest)) : Mono.just(words));
                    }
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    private List<Map<String, Object>> reduceListSize(List<Map<String, Object>> list, PaginationRequest paginationRequest) {
        return list.subList(paginationRequest.getRecordFrom(), paginationRequest.getRecordTo()+1);
    }

    public Mono<String> deleteWord(InputRequest inputRequest) {
        return Mono.fromCallable(() -> jdbcTemplate.update(DELETE_WORD, inputRequest.getWord()))
                .flatMap(rowsDeleted -> {
                    if(rowsDeleted == 0) {
                        return Mono.defer(() -> Mono.error(new AccertifyRuntimeException("Record not found in database")));
                    }
                    else {
                        return Mono.defer(() -> Mono.just("Deleted " + rowsDeleted + " rows"));
                    }
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<String> addWord(String word, List<String> subWords) {
        try {
            // Execute the insert query
            jdbcTemplate.execute(ADD_WORD, (PreparedStatementCallback<Object>) preparedStatement -> {
                // Set values for the parameters in the insert query
                preparedStatement.setString(1, word);
                preparedStatement.setArray(2, createSqlArray(subWords));
                // Execute the insert query
                return preparedStatement.execute();
            });
        } catch (DuplicateKeyException e) {
            throw new AccertifyRuntimeException("Value already exists in the database ");
        }
        return Mono.just("Inserted word: " + word + " and its " + subWords.size() + " subwords into the database");
    }

    private Array createSqlArray(List<String> list){
        Array subWordArray = null;
        DataSource dataSource = jdbcTemplate.getDataSource();
        if (dataSource != null) {
            try (Connection connection = dataSource.getConnection()) {
                subWordArray = connection.createArrayOf("varchar", list.toArray());
            } catch (SQLException e) {
                throw new AccertifyRuntimeException("Error converting list of subwords to array before insertion ", e);
            }
        } else {
            throw new IllegalStateException("Data source is null.");
        }
        return subWordArray;
    }
}
