package edu.epam.taskxml.entity;

public class Hotel {
    private int starsCount;
    private FoodType foodType;
    private int placeCount;
    private boolean airConditioningPresent;
    private boolean tvPresent;

    public Hotel() {
    }

    public Hotel(int stars, FoodType foodType, int placeCount, boolean airConditioning,
                 boolean tv) {
        this.starsCount = stars;
        this.foodType = foodType;
        this.placeCount = placeCount;
        this.airConditioningPresent = airConditioning;
        this.tvPresent = tv;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public int getPlaceCount() {
        return placeCount;
    }

    public void setPlaceCount(int placeCount) {
        this.placeCount = placeCount;
    }

    public boolean isAirConditioningPresent() {
        return airConditioningPresent;
    }

    public void setAirConditioningPresent(boolean airConditioningPresent) {
        this.airConditioningPresent = airConditioningPresent;
    }

    public boolean isTvPresent() {
        return tvPresent;
    }

    public void setTvPresent(boolean tvPresent) {
        this.tvPresent = tvPresent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Hotel{");
        sb.append("starsCount=").append(starsCount);
        sb.append(", foodType=").append(foodType);
        sb.append(", placeCount=").append(placeCount);
        sb.append(", airConditioningPresent=").append(airConditioningPresent);
        sb.append(", tvPresent=").append(tvPresent);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel)) return false;
        Hotel hotel = (Hotel) o;
        return starsCount == hotel.starsCount
                && placeCount == hotel.placeCount
                && airConditioningPresent == hotel.airConditioningPresent
                && tvPresent == hotel.tvPresent
                && foodType == hotel.foodType;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result += 31 * result + starsCount;
        result += 31 * result + foodType.hashCode();
        result += 31 * result + placeCount;
        result += 31 * result + Boolean.hashCode(airConditioningPresent);
        result += 31 * result + Boolean.hashCode(tvPresent);
        return result;
    }
}
