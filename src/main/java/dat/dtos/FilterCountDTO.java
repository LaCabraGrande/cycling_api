package dat.dtos;

import java.util.Map;

public class FilterCountDTO {
    private Map<String, Integer> gearSeriesCount;
    private Map<String, Integer> saddleBrandCount;
    private Map<String, Integer> wheelBrandCount;
    private Map<String, Integer> bicycleBrandCount;
    private Map<String, Integer> bicycleTypeCount;
    private Map<String, Integer> wheelTypeCount;
    private Map<String, Integer> priceIntervalCount; // Nyt felt for prisintervaller

    // Constructor
    public FilterCountDTO(Map<String, Integer> gearSeriesCount,
                          Map<String, Integer> saddleBrandCount,
                          Map<String, Integer> wheelBrandCount,
                          Map<String, Integer> bicycleBrandCount,
                          Map<String, Integer> bicycleTypeCount,
                          Map<String, Integer> wheelTypeCount,
                          Map<String, Integer> priceIntervalCount) {
        this.gearSeriesCount = gearSeriesCount;
        this.saddleBrandCount = saddleBrandCount;
        this.wheelBrandCount = wheelBrandCount;
        this.bicycleBrandCount = bicycleBrandCount;
        this.bicycleTypeCount = bicycleTypeCount;
        this.wheelTypeCount = wheelTypeCount;
        this.priceIntervalCount = priceIntervalCount;
    }

    // Getters and Setters
    public Map<String, Integer> getGearSeriesCount() {
        return gearSeriesCount;
    }

    public void setGearSeriesCount(Map<String, Integer> gearSeriesCount) {
        this.gearSeriesCount = gearSeriesCount;
    }

    public Map<String, Integer> getSaddleBrandCount() {
        return saddleBrandCount;
    }

    public void setSaddleBrandCount(Map<String, Integer> saddleBrandCount) {
        this.saddleBrandCount = saddleBrandCount;
    }

    public Map<String, Integer> getWheelBrandCount() {
        return wheelBrandCount;
    }

    public void setWheelBrandCount(Map<String, Integer> wheelBrandCount) {
        this.wheelBrandCount = wheelBrandCount;
    }

    public Map<String, Integer> getBicycleBrandCount() {
        return bicycleBrandCount;
    }

    public void setBicycleBrandCount(Map<String, Integer> bicycleBrandCount) {
        this.bicycleBrandCount = bicycleBrandCount;
    }

    public Map<String, Integer> getBicycleTypeCount() {
        return bicycleTypeCount;
    }

    public void setBicycleTypeCount(Map<String, Integer> bicycleTypeCount) {
        this.bicycleTypeCount = bicycleTypeCount;
    }

    public Map<String, Integer> getWheelTypeCount() {
        return wheelTypeCount;
    }

    public void setWheelTypeCount(Map<String, Integer> wheelTypeCount) {
        this.wheelTypeCount = wheelTypeCount;
    }

    public Map<String, Integer> getPriceIntervalCount() {
        return priceIntervalCount;
    }

    public void setPriceIntervalCount(Map<String, Integer> priceIntervalCount) {
        this.priceIntervalCount = priceIntervalCount;
    }

    @Override
    public String toString() {
        return "FilterCountDTO{" +
                "gearSeriesCount=" + gearSeriesCount +
                ", saddleBrandCount=" + saddleBrandCount +
                ", wheelBrandCount=" + wheelBrandCount +
                ", bicycleBrandCount=" + bicycleBrandCount +
                ", bicycleTypeCount=" + bicycleTypeCount +
                ", wheelTypeCount=" + wheelTypeCount +
                ", priceIntervalCount=" + priceIntervalCount +
                '}';
    }
}
