package org.tc.rs.source.services;

import org.tc.rs.source.rest.entities.SourceRecord;
import reactor.core.publisher.Flux;

public interface SourceBExecutor {

    Flux<SourceRecord> fetchRecords();


}
