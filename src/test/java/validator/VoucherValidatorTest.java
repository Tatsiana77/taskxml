package validator;

import edu.epam.taskxml.validator.VoucherValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class VoucherValidatorTest {
    private VoucherValidator validator;

    @BeforeClass
    public void createValidator() {
        validator = new VoucherValidator();
    }

    @Test
    public void isValidXmlFileTestValid() {
        File schemaFile = new File(getClass().getClassLoader().getResource("files/vouchersTest.xsd").getFile());
        File dataFile = new File(getClass().getClassLoader().getResource("files/vouchersTest.xml").getFile());
        assertTrue(validator.isValidXmlFile(dataFile.getAbsolutePath(), schemaFile.getAbsolutePath()));
    }

    @Test
    public void isValidXmlFileTestInvalid() {
        File schemaFile = new File(getClass().getClassLoader().getResource("files/vouchersTest.xsd").getFile());
        File dataFile = new File(getClass().getClassLoader().getResource("files/vouchersInvalidTest.xml").getFile());
        assertFalse(validator.isValidXmlFile(dataFile.getAbsolutePath(), schemaFile.getAbsolutePath()));
    }

}
