package dat.dtos;

import dat.entities.Bicycle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BicycleDTO {

    private int id;
    private String brand;
    private String model;
    private int size;
    private int price;
    private double weight;
    private String description;
    private FrameDTO frame;
    private GearDTO gear;
    private WheelDTO wheel;
    private SaddleDTO saddle;

    // Constructor med alle felter
    public BicycleDTO(int id, String brand, String model, int size, int price, double weight ,String description, FrameDTO frame, GearDTO gear, WheelDTO wheel, SaddleDTO saddle) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.size = size;
        this.price = price;
        this.weight = weight;
        this.description = description;
        this.frame = frame;
        this.gear = gear;
        this.wheel = wheel;
        this.saddle = saddle;
    }

    public BicycleDTO(String brand, String model, int size, int price, double weight, String description) {
        this.brand = brand;
        this.model = model;
        this.size = size;
        this.price = price;
        this.weight = weight;
        this.description = description;
    }

    // Constructor baseret på Bicycle-entitet
    public BicycleDTO(Bicycle bicycle) {
        this.id = bicycle.getId();
        this.brand = bicycle.getBrand();
        this.model = bicycle.getModel();
        this.size = bicycle.getSize();
        this.price = bicycle.getPrice();
        this.weight = bicycle.getWeight();
        this.description = bicycle.getDescription();

        System.out.println("Constructing BicycleDTO, frame: " + bicycle.getFrame());
        if (bicycle.getFrame() != null) {
            this.frame = new FrameDTO(bicycle.getFrame());
        }

        System.out.println("Constructing BicycleDTO, gear: " + bicycle.getGear());
        if (bicycle.getGear() != null) {
            this.gear = new GearDTO(bicycle.getGear());
        }

        System.out.println("Constructing BicycleDTO, wheel: " + bicycle.getWheel());
        if (bicycle.getWheel() != null) {
            this.wheel = new WheelDTO(bicycle.getWheel());
        }

        System.out.println("Constructing BicycleDTO, saddle: " + bicycle.getSaddle());
        if (bicycle.getSaddle() != null) {
            this.saddle = new SaddleDTO(bicycle.getSaddle());
        }
    }


    public Bicycle toEntity() {
        return new Bicycle(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BicycleDTO)) return false;
        BicycleDTO that = (BicycleDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}

