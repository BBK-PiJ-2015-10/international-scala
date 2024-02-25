package org.tc.rs.source.rest.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.tc.rs.source.rest.entities.SourceRecord;
import org.tc.rs.source.rest.mappers.SourceRecordMapper;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class SourceAImpl implements SourceA {

    Logger logger = Logger.getLogger(this.getClass().getName());

    private WebClient webClient;

    private URI sourceRecordURI;

    private SourceRecordMapper mapper;

    @Autowired
    public SourceAImpl(@Value("${app.source.a.url}") String sourceRecordUrl, @Value("${app.source.a.port}") int sourceRecordPort,
                       @Value("${app.source.a.path}") String sourceRecordPath, SourceRecordMapper mapper) {
        this.webClient = WebClient.builder().build();
        this.sourceRecordURI = URI.create("http://" + sourceRecordUrl + ":" + sourceRecordPort + sourceRecordPath);
        this.mapper = mapper;
    }
    
    @Override
    public Mono<SourceRecord> fetchRecord() {
        return webClient.get().uri(sourceRecordURI).retrieve()
                .bodyToMono(String.class)
                .log()
                .map(this::mapJsonToRecord).flatMap(
                        optional -> optional.map(Mono::just).orElseGet(Mono::empty)
                );
    }

    private Optional<SourceRecord> mapJsonToRecord(String jsonResponse) {
        try {
            logger.info(String.format("Processing jsonResponse to %s", jsonResponse));
            var record = mapper.fromJson(jsonResponse);
            return record;
        } catch (Exception e) {
            logger.warning(String.format("Unable to parse response due to %s", e));
            return Optional.empty();
        }
    }

}
