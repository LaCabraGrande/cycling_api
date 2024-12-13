package dat.dtos;

import java.util.Map;

public class FilterCountDTO {
    private Map<String, Integer> gearSeriesCount;
    private Map<String, Integer> saddleBrandCount;
    private Map<String, Integer> wheelBrandCount;

    // Constructor
    public FilterCountDTO(Map<String, Integer> gearSeriesCount,
                          Map<String, Integer> saddleBrandCount,
                          Map<String, Integer> wheelBrandCount) {
        this.gearSeriesCount = gearSeriesCount;
        this.saddleBrandCount = saddleBrandCount;
        this.wheelBrandCount = wheelBrandCount;
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

    @Override
    public String toString() {
        return "FilterCountDTO{" +
                "gearSeriesCount=" + gearSeriesCount +
                ", saddleBrandCount=" + saddleBrandCount +
                ", wheelBrandCount=" + wheelBrandCount +
                '}';
    }
}
