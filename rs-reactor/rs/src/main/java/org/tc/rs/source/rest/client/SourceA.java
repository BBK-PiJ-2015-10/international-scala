package org.tc.rs.source.rest.client;

import org.tc.rs.source.rest.entities.SourceRecord;
import reactor.core.publisher.Mono;

public interface SourceA {

    Mono<SourceRecord> fetchRecord();

}
