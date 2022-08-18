package edu.epam.taskxml.entity;

import java.time.LocalDateTime;

public class BeachVacationVoucher extends AbstractVoucher {
    private int distanceToBeach;

    public BeachVacationVoucher() {
    }

    public BeachVacationVoucher(String id, String webSite, CountryType country,
                                LocalDateTime departure, LocalDateTime arrival,
                                Hotel hotel, int cost, TransportType transportType,
                                int distanceToBeach) {
        super(id, webSite, country, departure, arrival, hotel, cost, transportType);
        this.distanceToBeach = distanceToBeach;
    }

    public int getDistanceToBeach() {
        return distanceToBeach;
    }

    public void setDistanceToBeach(int distanceToBeach) {
        this.distanceToBeach = distanceToBeach;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BeachVacationVoucher that = (BeachVacationVoucher) o;
        return distanceToBeach == that.distanceToBeach;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result += 31 * result + super.hashCode();
        result += 31 * result + distanceToBeach;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BeachVacationVoucher{");
        sb.append(super.toString());
        sb.append("distanceToBeach=").append(distanceToBeach);
        sb.append('}');
        return sb.toString();
    }
}
