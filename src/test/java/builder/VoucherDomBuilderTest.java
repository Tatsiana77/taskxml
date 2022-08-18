package builder;

import edu.epam.taskxml.builder.AbstractVoucherBuilder;
import edu.epam.taskxml.builder.VoucherDomBuilder;
import edu.epam.taskxml.entity.*;
import edu.epam.taskxml.exeption.VoucherException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class VoucherDomBuilderTest {
    private AbstractVoucherBuilder builder;

    @BeforeClass
    public void createBuilder() {
        builder = new VoucherDomBuilder();
    }

    @Test(dataProvider = "validData")
    public void testBuildVouchers(String path, Set<AbstractVoucher> expected) throws VoucherException, VoucherException {
        builder.buildVouchers(path);
        Set<AbstractVoucher> vouchers = builder.getVouchers();
        assertEquals(vouchers, expected);
    }

    @DataProvider(name = "validData")
    public Object[][] createXmlSetData() {

        AbstractVoucher firstElement = new BeachVacationVoucher(
                "id1",
                "https://www.tour1.com",
                CountryType.USA,
                LocalDateTime.parse("2001-11-17T06:00:00"),
                LocalDateTime.parse("2001-12-03T06:00:00"),
                new Hotel(5,
                        FoodType.AL,
                        4,
                        true,
                        true),
                1232,
                TransportType.AUTO,
                400
        );


        AbstractVoucher secondElement = new PilgrimageVoucher(
                "id2",
                "https://www.tour.com",
                CountryType.EGYPT,
                LocalDateTime.parse("2001-11-17T06:00:00"),
                LocalDateTime.parse("2001-12-03T06:00:00"),
                new Hotel(3,
                        FoodType.AL,
                        3,
                        true,
                        true),
                3123,
                TransportType.AUTO,
                true
        );

        AbstractVoucher thirdElement = new BeachVacationVoucher(
                "id3",
                "https://www.tour1.com",
                CountryType.USA,
                LocalDateTime.parse("2001-11-17T06:00:00"),
                LocalDateTime.parse("2001-12-03T06:00:00"),
                new Hotel(5,
                        FoodType.AL,
                        4,
                        true,
                        true),
                1232,
                TransportType.AUTO,
                400
        );

        Set<AbstractVoucher> expected = new HashSet<>();
        expected.add(firstElement);
        expected.add(secondElement);
        expected.add(thirdElement);
        File file = new File(getClass().getClassLoader().getResource("files/vouchersTest.xml").getFile());

        return new Object[][]{
                {file.getAbsolutePath(), expected}
        };
    }
}
