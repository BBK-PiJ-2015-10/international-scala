package org.tc.rs.source.rest.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;
import org.tc.rs.source.rest.entities.SourceRecord;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;
import java.util.logging.Logger;

public class SourceJsonImplementation implements Source {

    Logger logger = Logger.getLogger(this.getClass().getName());

    private WebClient webClient;

    private URI sourceRecordURI;

    ObjectMapper objectMapper = new ObjectMapper();

    public SourceJsonImplementation(String sourceRecordUrl, int sourceRecordPort, String sourceRecordPath) {
        this.webClient = WebClient.builder().build();
        this.sourceRecordURI = URI.create("http://" + sourceRecordUrl + ":" + sourceRecordPort + sourceRecordPath);
    }

    private Optional<SourceRecord> mapJsonToRecord(String jsonResponse) {
        try {
            logger.info(String.format("Processing jsonResponse to %s", jsonResponse));
            SourceRecord record = objectMapper.readValue(jsonResponse, SourceRecord.class);
            return Optional.of(record);
        } catch (Exception e) {
            logger.warning(String.format("Unable to parse response due to %s", e));
            return Optional.empty();
        }
    }

    @Override
    public Mono<Optional<SourceRecord>> fetchRecord() {
        return webClient.get().uri(sourceRecordURI).retrieve().bodyToMono(String.class).map(this::mapJsonToRecord);
    }
}
