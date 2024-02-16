package org.tc.rs.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MyTesterImpl implements MyTester {

    private Logger LOGGER = Logger.getLogger(MyTesterImpl.class.getName());

    private MyPrinter myPrinter;

    @Autowired
    public MyTesterImpl(MyPrinter myPrinter) {
        this.myPrinter = myPrinter;
    }

    @Override
    public void execute() {
        LOGGER.log(Level.INFO,"delegating to MyPrinter");
        myPrinter.printName();
    }
}
