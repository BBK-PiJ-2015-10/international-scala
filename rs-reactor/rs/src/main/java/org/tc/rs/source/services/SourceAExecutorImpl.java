package org.tc.rs.source.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tc.rs.source.rest.client.SourceA;
import org.tc.rs.source.rest.entities.SourceRecord;
import reactor.core.publisher.Flux;


@Component
public class SourceAExecutorImpl {

    private SourceA sourceA;

    private boolean done;


    public SourceAExecutorImpl(SourceA sourceA) {
        this.sourceA = sourceA;
        done = false;
    }


    public Flux<SourceRecord> fetchRecords() {
        return fetchRecordsHelper();
    }

    private Flux<SourceRecord> fetchRecordsHelper() {
        return Flux.from(sourceA.fetchRecord().expand(resp -> sourceA.fetchRecord())).log();
    }

}
