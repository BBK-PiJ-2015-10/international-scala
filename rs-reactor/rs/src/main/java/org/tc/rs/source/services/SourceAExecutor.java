package org.tc.rs.source.services;

import org.tc.rs.source.rest.entities.SourceRecord;
import reactor.core.publisher.Flux;

public interface SourceAExecutor {

    Flux<SourceRecord> fetchRecords();

}
