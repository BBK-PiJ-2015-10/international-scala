package org.tc.rs.source.rest.client;

import org.tc.rs.source.rest.entities.SourceRecord;
import org.tc.rs.source.rest.mappers.SourceRecordMapper;
import reactor.core.publisher.Mono;

public interface SourceB {

    Mono<SourceRecord> fetchRecord();
}
