package edu.epam.taskxml.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class VoucherValidator {
    private static final Logger logger = LogManager.getLogger();

    public boolean isValidXmlFile(String path, String schemaPath){
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(schemaPath);
        boolean result = true;
        try {
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(path);
            validator.validate(source);
            logger.log(Level.INFO,"File: {} is valid", path);
        } catch (SAXException e) {
            logger.log(Level.INFO,"File: {} is not valid; message: {}", path, e.getMessage());
            result = false;
        } catch (IOException e) {
            logger.log(Level.INFO, "Error while reading file: {}", path);
            result = false;
        }
        return result;
    }
}
