package org.tc.rs.source.rest.mappers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.tc.rs.source.rest.entities.SourceRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Optional;
import java.util.logging.Logger;

import java.util.logging.Level;

@Component
public class SourceRecordMapperImpl implements SourceRecordMapper {

    Logger logger = Logger.getLogger(this.getClass().getName());

    ObjectMapper objectMapper = new ObjectMapper();

    DocumentBuilderFactory factory;

    DocumentBuilder dBuilder;

    public SourceRecordMapperImpl() {
        try {
            dBuilder = factory.newDocumentBuilder();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to start document builder", e);
        }
    }


    @Override
    public Optional<SourceRecord> fromJson(String jsonString) {
        try {
            logger.info(String.format("Processing jsonResponse to %s", jsonString));
            SourceRecord record = objectMapper.readValue(jsonString, SourceRecord.class);
            return Optional.of(record);
        } catch (Exception e) {
            logger.warning(String.format("Unable to parse response due to %s", e));
            Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<SourceRecord> fromXML(String xmlString) {
        try {
            Document doc = dBuilder.parse(new File(xmlString));
            NodeList msgList = doc.getElementsByTagName("msg");
            if (msgList.getLength() > 1) {
                Node msg = msgList.item(0);
                NodeList childrenMsg = msg.getChildNodes();
                if (childrenMsg.getLength() > 1) {
                    Node childMsg = childrenMsg.item(0);
                    if (childMsg.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) childMsg;
                        String id = elem.getAttribute("value");
                        if (id.isEmpty()) {
                            String done = elem.getNodeValue();
                            if (done.equals("done")) {
                                SourceRecord record = new SourceRecord("done", Optional.empty());
                                return Optional.of(record);
                            } else {
                                return Optional.empty();
                            }
                        } else {
                            SourceRecord record = new SourceRecord("ok", Optional.of(id));
                            return Optional.of(record);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to parse response to xml due to", e);
            return Optional.empty();
        }
        return Optional.empty();
    }


}
