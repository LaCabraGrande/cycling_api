package dat.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BicycleCompleteDTO {

    private int id;
    private String brand;
    private String model;
    private int size;
    private int price;
    private double weight;
    private String link;
    private String username;
    private int frameId;
    private int gearId;
    private int wheelId;
    private int saddleId;

    // Constructor med alle felter
    public BicycleCompleteDTO(String brand, String model, int size, int price, double weight , String link, String username, int frameId, int gearId, int wheelId, int saddleId) {
        this.brand = brand;
        this.model = model;
        this.size = size;
        this.price = price;
        this.weight = weight;
        this.link = link;
        this.username = username;
        this.frameId = frameId;
        this.gearId = gearId;
        this.wheelId = wheelId;
        this.saddleId = saddleId;
    }
}

