package edu.epam.taskxml.entity;

import java.time.LocalDateTime;

public class AbstractVoucher {
    public static final String DEFAULT_WEBSITE = "https://www.otpusk.com";
    private String id;
    private String webSite;
    private CountryType country;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private Hotel hotel = new Hotel();
    private int cost;
    private TransportType transportType;

    public AbstractVoucher() {
    }

    public AbstractVoucher(String id, String webSite, CountryType country,
                           LocalDateTime departure, LocalDateTime arrival,
                           Hotel hotel, int cost, TransportType transportType) {
        this.id = id;
        this.webSite = webSite;
        this.country = country;
        this.departure = departure;
        this.arrival = arrival;
        this.hotel = hotel;
        this.cost = cost;
        this.transportType = transportType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public CountryType getCountry() {
        return country;
    }

    public void setCountry(CountryType country) {
        this.country = country;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AbstractVoucher{");
        sb.append("id='").append(id).append('\'');
        sb.append(", webSite='").append(webSite).append('\'');
        sb.append(", country=").append(country);
        sb.append(", departure=").append(departure);
        sb.append(", arrival=").append(arrival);
        sb.append(", hotel=").append(hotel);
        sb.append(", cost=").append(cost);
        sb.append(", transportType=").append(transportType);
        sb.append('}');
        return sb.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractVoucher voucher = (AbstractVoucher) o;
        return cost == voucher.cost &&
                webSite.equals(voucher.webSite)
                && country == voucher.country
                && departure.equals(voucher.departure)
                && arrival.equals(voucher.arrival)
                && hotel.equals(voucher.hotel)
                && transportType == voucher.transportType;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result += 31 * result + webSite.hashCode();
        result += 31 * result + country.hashCode();
        result += 31 * result + departure.hashCode();
        result += 31 * result + arrival.hashCode();
        result += 31 * result + hotel.hashCode();
        result += 31 * result + cost;
        result += 31 * result + transportType.hashCode();
        return result;
    }

    public void setTransport(TransportType valueOf) {
    }
}
