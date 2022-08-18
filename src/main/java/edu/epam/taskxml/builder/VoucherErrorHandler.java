package edu.epam.taskxml.builder;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class VoucherErrorHandler  implements ErrorHandler {
    private static final Logger logger = LogManager.getLogger();
    private static final String ERROR_TEMPLATE = "position: {}\nmessage: {}";

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        logger.log(Level.WARN, ERROR_TEMPLATE, getLineColumnNumber(exception), exception.getMessage());
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        logger.log(Level.ERROR, ERROR_TEMPLATE, getLineColumnNumber(exception), exception.getMessage());
        throw exception;
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        logger.log(Level.FATAL, ERROR_TEMPLATE, getLineColumnNumber(exception), exception.getMessage());
        throw exception;
    }

    private String getLineColumnNumber(SAXParseException exception) {
        return exception.getLineNumber() + " : " + exception.getColumnNumber();
    }
}
