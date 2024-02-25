package org.tc.rs.source.rest.mappers;

import org.tc.rs.source.rest.entities.SourceRecord;

import java.util.Optional;

public interface SourceRecordMapper {

    Optional<SourceRecord> fromXML(String xmlString);

    Optional<SourceRecord> fromJson(String xmlString);

}
