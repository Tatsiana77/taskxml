package edu.epam.taskxml.builder;

import edu.epam.taskxml.exeption.VoucherException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class VoucherSaxBuilder extends AbstractVoucherBuilder {

    private static final Logger logger = LogManager.getLogger();


    @Override
    public void buildVouchers(String path) throws VoucherException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            var handler = new VoucherHandler();
            reader.setContentHandler(handler);
            reader.setErrorHandler(new VoucherErrorHandler());
            reader.parse(path);
            vouchers = handler.getVouchers();
        } catch (EnumConstantNotPresentException | SAXException e) {
            logger.log(Level.ERROR, "Error occurred while parsing file {}; message:  {}", path, e.getMessage());
            throw new VoucherException("Error occurred while parsing file " + path + "; message:  " + e.getMessage());
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error occurred while reading file {}; message:  {}", path, e.getMessage());
            throw new VoucherException("Error occurred while reading file " + path + "; message:  " + e.getMessage());
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, "Error occurred while configuring; message:  {}", e.getMessage());
            throw new VoucherException("Error occurred while configuring; message:  " + e.getMessage());
        }
        logger.log(Level.INFO, "Vouchers were successfully built. File: {}", path);
    }

}


