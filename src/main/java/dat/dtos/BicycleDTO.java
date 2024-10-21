package dat.dtos;

import dat.entities.Bicycle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import dat.dtos.FrameDTO;
import dat.dtos.GearDTO;
import dat.dtos.WheelDTO;
import dat.dtos.SaddleDTO;

@Getter
@Setter
@NoArgsConstructor
public class BicycleDTO {

    private int id;
    private String brand;
    private String model;
    private String size;
    private int price;
    private String description;
    private FrameDTO frame;
    private GearDTO gear;
    private WheelDTO wheel;
    private SaddleDTO saddle;

    // Constructor med alle felter
    public BicycleDTO(int id, String brand, String model, String size, int price, String description, FrameDTO frame, GearDTO gear, WheelDTO wheel, SaddleDTO saddle) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.size = size;
        this.price = price;
        this.description = description;
        this.frame = frame;
        this.gear = gear;
        this.wheel = wheel;
        this.saddle = saddle;
    }

    // Constructor baseret på Bicycle-entitet
    public BicycleDTO(Bicycle bicycle) {
        this.id = bicycle.getId();
        this.brand = bicycle.getBrand();
        this.model = bicycle.getModel();
        this.size = bicycle.getSize();
        this.price = bicycle.getPrice();
        this.description = bicycle.getDescription();
        this.frame = new FrameDTO(bicycle.getFrame());
        this.gear = new GearDTO(bicycle.getGear());
        this.wheel = new WheelDTO(bicycle.getWheel());
        this.saddle = new SaddleDTO(bicycle.getSaddle());
    }

    public Bicycle toEntity() {
        return new Bicycle(this);
    }

}

