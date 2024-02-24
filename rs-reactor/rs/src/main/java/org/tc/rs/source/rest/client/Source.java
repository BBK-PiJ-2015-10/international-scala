package org.tc.rs.source.rest.client;

import org.tc.rs.source.rest.entities.SourceRecord;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface Source {

    Mono<Optional<SourceRecord>> fetchRecord();

}
