package edu.epam.taskxml.builder;

import edu.epam.taskxml.entity.*;
import edu.epam.taskxml.exeption.VoucherException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;


public class VoucherDomBuilder  extends AbstractVoucherBuilder{
    private static final Logger logger = LogManager.getLogger();
    private DocumentBuilder builder;

    public VoucherDomBuilder(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, "Configuration failed {}", e.getMessage());
        }
    }
    @Override
    public void buildVouchers(String path) throws VoucherException {
        Document doc;
        try {
            doc = builder.parse(path);
            Element root = doc.getDocumentElement();
            NodeList voucherList = root.getElementsByTagName(Voucher.PILGRIMAGE_VOUCHER.toString());
            for (int i = 0; i < voucherList.getLength(); i++) {
                Element voucherElement = (Element) voucherList.item(i);
                AbstractVoucher voucher = buildVoucher(voucherElement);
                vouchers.add(voucher);
            }
            voucherList = root.getElementsByTagName(Voucher.BEACH_VACATION_VOUCHER.toString());
            for (int i = 0; i < voucherList.getLength(); i++) {
                Element voucherElement = (Element) voucherList.item(i);
                AbstractVoucher voucher = buildVoucher(voucherElement);
                vouchers.add(voucher);
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error occurred while reading file {}; message:  {}", path, e.getMessage());
            throw new VoucherException("Error occurred while reading file " + path + "; message:  " + e.getMessage());
        } catch (SAXException e) {
            logger.log(Level.ERROR, "Error occurred while parsing file {}; message:  {}", path, e.getMessage());
            throw new VoucherException("Error occurred while parsing file " + path + "; message:  " + e.getMessage());
        }
        logger.log(Level.INFO, "Vouchers were successfully built. File: {}", path);
    }

    private AbstractVoucher buildVoucher(Element voucherElement) {
        AbstractVoucher voucher = voucherElement.getTagName().equals(Voucher.PILGRIMAGE_VOUCHER.toString()) ?
                new PilgrimageVoucher() :
                new BeachVacationVoucher();

        String websiteAttribute = voucherElement.getAttribute("web-site");

        if (websiteAttribute.isBlank()) {
            voucher.setWebSite(AbstractVoucher.DEFAULT_WEBSITE);
        } else {
            voucher.setWebSite(websiteAttribute);
        }

        voucher.setId(voucherElement.getAttribute("id"));
        String data = getElementTextContent(voucherElement, Voucher.COUNTRY.toString());
        voucher.setCountry(CountryType.valueOf(data.toUpperCase()));
        data = getElementTextContent(voucherElement, Voucher.DEPARTURE_DATE_TIME.toString());
        voucher.setDeparture(LocalDateTime.parse(data));
        data = getElementTextContent(voucherElement, Voucher.ARRIVAL_DATE_TIME.toString());
        voucher.setArrival(LocalDateTime.parse(data));
        data = getElementTextContent(voucherElement, Voucher.TRANSPORT_TYPE.toString());
        voucher.setTransport(TransportType.valueOf(data.toUpperCase()));
        data = getElementTextContent(voucherElement, Voucher.COST.toString());
        voucher.setCost(Integer.parseInt(data));

        if (voucher.getClass() == PilgrimageVoucher.class) {
            data = getElementTextContent(voucherElement, Voucher.PILGRIMAGE_PASSPORT_REQUIRED.toString());
            ((PilgrimageVoucher) voucher).setPilgrimagePassportRequired(Boolean.parseBoolean(data));
        } else {
            data = getElementTextContent(voucherElement, Voucher.DISTANCE_TO_BEACH.toString());
            ((BeachVacationVoucher) voucher).setDistanceToBeach(Integer.parseInt(data));
        }

        Hotel hotel = voucher.getHotel();
        Element hotelElement =
                (Element) voucherElement.getElementsByTagName(Voucher.HOTEL.toString()).item(0);

        data = getElementTextContent(hotelElement, Voucher.PLACE_COUNT.toString());
        hotel.setPlaceCount(Integer.parseInt(data));
        data = getElementTextContent(hotelElement, Voucher.STARS.toString());
        hotel.setStarsCount(Integer.parseInt(data));
        data = getElementTextContent(hotelElement, Voucher.FOOD.toString());
        hotel.setFoodType(FoodType.valueOf(data.toUpperCase(Locale.ROOT)));
        data = getElementTextContent(hotelElement, Voucher.AIR_CONDITIONING.toString());
        hotel.setAirConditioningPresent(Boolean.parseBoolean(data));
        data = getElementTextContent(hotelElement, Voucher.TV.toString());
        hotel.setTvPresent(Boolean.parseBoolean(data));
        return voucher;

    }
    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }

}
