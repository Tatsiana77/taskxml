package edu.epam.taskxml.entity;

import java.time.LocalDateTime;

public class PilgrimageVoucher  extends AbstractVoucher{
    private boolean pilgrimagePassportRequired;

    public PilgrimageVoucher() {
    }

    public PilgrimageVoucher(String id, String webSite, CountryType country, LocalDateTime departure,
                             LocalDateTime arrival, Hotel hotel, int cost, TransportType transportType,
                             boolean pilgrimagePassportRequired) {
        super(id, webSite, country, departure, arrival, hotel, cost, transportType);
        this.pilgrimagePassportRequired = pilgrimagePassportRequired;
    }

    public boolean isPilgrimagePassportRequired() {
        return pilgrimagePassportRequired;
    }

    public void setPilgrimagePassportRequired(boolean pilgrimagePassportRequired) {
        this.pilgrimagePassportRequired = pilgrimagePassportRequired;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PilgrimageVoucher{");
        sb.append(super.toString());
        sb.append("pilgrimagePassportRequired=").append(pilgrimagePassportRequired);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PilgrimageVoucher that = (PilgrimageVoucher) o;
        return pilgrimagePassportRequired == that.pilgrimagePassportRequired;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result += 31 * result + super.hashCode();
        result += 31 * result + Boolean.hashCode(pilgrimagePassportRequired);
        return result;
    }
}
