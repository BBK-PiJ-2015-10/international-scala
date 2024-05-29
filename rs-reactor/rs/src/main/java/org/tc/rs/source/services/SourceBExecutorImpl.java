package org.tc.rs.source.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tc.rs.source.rest.client.SourceA;
import org.tc.rs.source.rest.client.SourceB;
import org.tc.rs.source.rest.entities.SourceRecord;
import reactor.core.publisher.Flux;

@Component
public class SourceBExecutorImpl {

    private SourceB sourceB;

    private boolean done;

    public SourceBExecutorImpl(SourceB sourceB) {
        this.sourceB = sourceB;
        done = false;
    }


    public Flux<SourceRecord> fetchRecords() {
        return fetchRecordsHelper();
    }

    private Flux<SourceRecord> fetchRecordsHelper() {
        return Flux.from(sourceB.fetchRecord().expand(resp -> sourceB.fetchRecord()));
    }
}
