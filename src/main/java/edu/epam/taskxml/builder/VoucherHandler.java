package edu.epam.taskxml.builder;

import edu.epam.taskxml.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class VoucherHandler  extends DefaultHandler {
    private static final char HYPHEN = '-';
    private static final char UNDERSCORE = '_';
    private final Set<AbstractVoucher> vouchers;
    private AbstractVoucher currentVoucher;
    private Voucher currentXmlTag;
    private final EnumSet<Voucher> withText;

    public VoucherHandler() {
        vouchers = new HashSet<>();
        withText = EnumSet.range(Voucher.COUNTRY, Voucher.TV);
    }

    public Set<AbstractVoucher> getVouchers() {
        return vouchers;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals(Voucher.BEACH_VACATION_VOUCHER.toString())
                || qName.equals(Voucher.PILGRIMAGE_VOUCHER.toString())) {
            currentVoucher = qName.equals(Voucher.PILGRIMAGE_VOUCHER.toString()) ?
                    new PilgrimageVoucher() :
                    new BeachVacationVoucher();
            if (attributes.getLength() == 1) {
                currentVoucher.setId(attributes.getValue(0));
                currentVoucher.setWebSite(AbstractVoucher.DEFAULT_WEBSITE);
            } else {
                int idAttributeIndex = attributes.getLocalName(0).equals(Voucher.ID.toString()) ? 0 : 1;
                int websiteAttributeIndex = 1 - idAttributeIndex;
                currentVoucher.setId(attributes.getValue(idAttributeIndex));
                currentVoucher.setWebSite(attributes.getValue(websiteAttributeIndex));
            }
        } else {
            Voucher temp = Voucher.valueOf(qName.toUpperCase().replace(HYPHEN, UNDERSCORE));
            if (withText.contains(temp)) {
                currentXmlTag = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals(Voucher.BEACH_VACATION_VOUCHER.toString())
                || qName.equals(Voucher.PILGRIMAGE_VOUCHER.toString())) {
            vouchers.add(currentVoucher);
            currentVoucher = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).strip();
        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case COUNTRY -> currentVoucher.setCountry(CountryType.valueOf(data.toUpperCase()));
                case DEPARTURE_DATE_TIME -> currentVoucher.
                        setDeparture(LocalDateTime.parse(data));
                case ARRIVAL_DATE_TIME -> currentVoucher.
                        setArrival(LocalDateTime.parse(data));
                case TRANSPORT_TYPE -> currentVoucher.setTransport(TransportType.valueOf(data.toUpperCase()));
                case COST -> currentVoucher.setCost(Integer.parseInt(data));
                case PLACE_COUNT -> currentVoucher.getHotel().setPlaceCount(Integer.parseInt(data));
                case STARS -> currentVoucher.getHotel().setStarsCount(Integer.parseInt(data));
                case FOOD -> currentVoucher.getHotel().setFoodType(FoodType.valueOf(data.toUpperCase()));
                case AIR_CONDITIONING -> currentVoucher.getHotel().
                        setAirConditioningPresent(Boolean.parseBoolean(data));
                case TV -> currentVoucher.getHotel().
                        setTvPresent(Boolean.parseBoolean(data));
                case DISTANCE_TO_BEACH -> {
                    BeachVacationVoucher tempVoucher = (BeachVacationVoucher) currentVoucher;
                    tempVoucher.setDistanceToBeach(Integer.parseInt(data));
                }
                case PILGRIMAGE_PASSPORT_REQUIRED -> {
                    PilgrimageVoucher tempVoucher = (PilgrimageVoucher) currentVoucher;
                    tempVoucher.setPilgrimagePassportRequired(Boolean.parseBoolean(data));
                }
                default -> throw new EnumConstantNotPresentException(
                        currentXmlTag.getDeclaringClass(), currentXmlTag.name());
            }
        }
        currentXmlTag = null;
    }
}
