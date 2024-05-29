package org.tc.rs.service;

import org.springframework.stereotype.Component;
import org.tc.rs.source.rest.client.SourceA;
import org.tc.rs.source.rest.mappers.SourceRecordMapper;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CoordinatorImpl implements Coordinator {

    private Logger logger = Logger.getLogger(this.getClass().getName());


    //private SourceAExecutor sourceAExecutor;

    private SourceRecordMapper mapper;

    private SourceA dog;

    public CoordinatorImpl(SourceRecordMapper mapper,SourceA cat) {
        this.mapper = mapper;
        this.dog = cat;
    }


    //@Autowired
    //public CoordinatorImpl(SourceAExecutor sourceAExecutor) {
      //  this.sourceAExecutor = sourceAExecutor;
    //}

    public Void execute(){

        boolean stop = true;

        while(stop){
            logger.log(Level.INFO,"Running like a champ");
            mapper.SayHi();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.info("Waking up");
            }
        }

        return null;
    }

}
