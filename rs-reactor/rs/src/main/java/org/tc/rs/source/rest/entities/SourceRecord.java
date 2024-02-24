package org.tc.rs.source.rest.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public record SourceRecord(@JsonProperty String status, @JsonProperty Optional<String> id) {

}
