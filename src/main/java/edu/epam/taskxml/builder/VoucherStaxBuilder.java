package edu.epam.taskxml.builder;

import edu.epam.taskxml.entity.*;
import edu.epam.taskxml.exeption.VoucherException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class VoucherStaxBuilder extends AbstractVoucherBuilder {
    private static final Logger logger = LogManager.getLogger();
    private static final char HYPHEN = '-';
    private static final char UNDERSCORE = '_';
    private final XMLInputFactory inputFactory;

    public VoucherStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildVouchers(String path) throws VoucherException {
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream = new FileInputStream(new File(path))) {
            reader = inputFactory.createXMLStreamReader(inputStream);

            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(Voucher.PILGRIMAGE_VOUCHER.toString())
                            || name.equals(Voucher.BEACH_VACATION_VOUCHER.toString())) {
                        AbstractVoucher voucher = buildVoucher(reader);
                        vouchers.add(voucher);
                    }
                }
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error occurred while reading file {}; message:  {}", path, e.getMessage());
            throw new VoucherException("Error occurred while reading file " + path + "; message:  " + e.getMessage());
        } catch (XMLStreamException e) {
            logger.log(Level.ERROR, "Error occurred while parsing file {}; message:  {}", path, e.getMessage());
            throw new VoucherException("Error occurred while parsing file " + path + "; message:  " + e.getMessage());
        }
        logger.log(Level.INFO, "Vouchers were successfully built. File: {}", path);
    }

    private AbstractVoucher buildVoucher(XMLStreamReader reader) throws XMLStreamException {
        AbstractVoucher voucher = reader.getLocalName().equals(Voucher.PILGRIMAGE_VOUCHER.toString()) ?
                new PilgrimageVoucher() :
                new BeachVacationVoucher();
        voucher.setId(reader.getAttributeValue(null, Voucher.ID.toString()));

        String webSiteAttr = reader.getAttributeValue(null, Voucher.WEB_SITE.toString());
        if (webSiteAttr == null) {
            webSiteAttr = AbstractVoucher.DEFAULT_WEBSITE;
        }
        voucher.setWebSite(webSiteAttr);

        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    String data = getXMLText(reader);
                    switch (Voucher.valueOf(name.toUpperCase().replace(HYPHEN, UNDERSCORE))) {
                        case COUNTRY -> voucher.setCountry(CountryType.valueOf(data.toUpperCase()));
                        case DEPARTURE_DATE_TIME -> voucher.setDeparture(LocalDateTime.parse(data));
                        case ARRIVAL_DATE_TIME -> voucher.setArrival(LocalDateTime.parse(data));
                        case TRANSPORT_TYPE -> voucher.setTransport(TransportType.valueOf(data.toUpperCase()));
                        case COST -> voucher.setCost(Integer.parseInt(data));
                        case DISTANCE_TO_BEACH -> {
                            assert voucher instanceof BeachVacationVoucher;
                            BeachVacationVoucher tempVoucher = (BeachVacationVoucher) voucher;
                            tempVoucher.setDistanceToBeach(Integer.parseInt(data));
                        }
                        case PILGRIMAGE_PASSPORT_REQUIRED -> {
                            assert voucher instanceof PilgrimageVoucher;
                            PilgrimageVoucher tempVoucher = (PilgrimageVoucher) voucher;
                            tempVoucher.setPilgrimagePassportRequired(Boolean.parseBoolean(data));
                        }
                        case HOTEL -> createXMLHotel(reader, voucher.getHotel());
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (name.equals(Voucher.PILGRIMAGE_VOUCHER.toString())
                            || name.equals(Voucher.BEACH_VACATION_VOUCHER.toString())) {
                        return voucher;
                    }
            }
        }
        throw new XMLStreamException("Unknown element in tag <student>");
    }

    private void createXMLHotel(XMLStreamReader reader, Hotel hotel)
            throws XMLStreamException {
        int type;
        String name;

        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    String data = getXMLText(reader);
                    switch (Voucher.valueOf(name.toUpperCase().replace(HYPHEN, UNDERSCORE))) {
                        case STARS -> hotel.setStarsCount(Integer.parseInt(data));
                        case FOOD -> hotel.setFoodType(FoodType.valueOf(data.toUpperCase()));
                        case AIR_CONDITIONING -> hotel.setAirConditioningPresent(Boolean.parseBoolean(data));
                        case TV -> hotel.setTvPresent(Boolean.parseBoolean(data));
                        case PLACE_COUNT -> hotel.setPlaceCount(Integer.parseInt(data));
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (Voucher.valueOf(name.toUpperCase().replace(HYPHEN, UNDERSCORE)) == Voucher.HOTEL) {
                        return;
                    }
            }
        }
        throw new XMLStreamException("Unknown element in tag <address>");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }

}

