package edu.epam.taskxml.main;

import edu.epam.taskxml.builder.AbstractVoucherBuilder;
import edu.epam.taskxml.builder.VoucherBuilderFactory;
import edu.epam.taskxml.exeption.VoucherException;
import edu.epam.taskxml.validator.VoucherValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;

public class VoucherMain {
    private static final Logger logger = LogManager.getLogger();
    private static final String RELATIVE_FILE_PATH = "files/voucher.xml";
    private static final String RELATIVE_SCHEMA_PATH = "files/voucher.xsd";

    public static void main(String[] args) {
        try {
            String filePath = convertToAbsolutePath(RELATIVE_FILE_PATH);
            String schemaPath = convertToAbsolutePath(RELATIVE_SCHEMA_PATH);
            VoucherValidator validator = new VoucherValidator();
            if (!validator.isValidXmlFile(filePath, schemaPath)) {
                return;
            }
            AbstractVoucherBuilder saxBuilder = VoucherBuilderFactory.createBuilder("sax");
            AbstractVoucherBuilder domBuilder = VoucherBuilderFactory.createBuilder("dom");
            AbstractVoucherBuilder staxBuilder = VoucherBuilderFactory.createBuilder("stax");
            saxBuilder.buildVouchers(filePath);
            domBuilder.buildVouchers(filePath);
            staxBuilder.buildVouchers(filePath);
            logger.log(Level.INFO, "Built successfully");
        } catch (VoucherException e) {
            logger.log(Level.FATAL, e.getMessage());
        }
    }

    private static String convertToAbsolutePath(String relativePath) throws VoucherException {
        if (relativePath == null) {
            logger.log(Level.ERROR, "Relative path is null");
            throw new VoucherException("Relative path is null");
        }
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL resourcePath = classLoader.getResource(relativePath);
        if (resourcePath == null) {
            logger.log(Level.ERROR, "File does not exist");
            throw new VoucherException("File does not exist");
        }
        File file = new File(resourcePath.getFile());
        return file.getAbsolutePath();
    }
}
