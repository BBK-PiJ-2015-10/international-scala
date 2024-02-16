package org.tc.rs.helper;

import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MyPrinterImpl implements MyPrinter{

    private Logger LOGGER = Logger.getLogger(MyPrinterImpl.class.getName());


    @Override
    public void printName() {
        LOGGER.log(Level.INFO,"alexis aleixs");
    }
}
