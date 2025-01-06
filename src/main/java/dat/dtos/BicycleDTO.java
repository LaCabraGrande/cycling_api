package dat.dtos;

import dat.entities.Bicycle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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
    private String link;
    private String username;
    private FrameDTO frame;
    private GearDTO gear;
    private WheelDTO wheel;
    private SaddleDTO saddle;

    // Constructor med alle felter
    public BicycleDTO(int id, String brand, String model, int size, int price, double weight , String link, String username, FrameDTO frame, GearDTO gear, WheelDTO wheel, SaddleDTO saddle) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.size = size;
        this.price = price;
        this.weight = weight;
        this.link = link;
        this.username = username;
        this.frame = frame;
        this.gear = gear;
        this.wheel = wheel;
        this.saddle = saddle;
    }

    public BicycleDTO(String brand, String model, int size, int price, double weight, String link, String username) {
        this.brand = brand;
        this.model = model;
        this.size = size;
        this.price = price;
        this.weight = weight;
        this.link = link;
        this.username = username;
    }

    // Constructor baseret på Bicycle-entitet
    public BicycleDTO(Bicycle bicycle) {
        this.id = bicycle.getId();
        this.brand = bicycle.getBrand();
        this.model = bicycle.getModel();
        this.size = bicycle.getSize();
        this.price = bicycle.getPrice();
        this.weight = bicycle.getWeight();
        this.link = bicycle.getLink();
        this.username = bicycle.getUsername();

        if (bicycle.getFrame() != null) {
            this.frame = new FrameDTO(bicycle.getFrame());
        }

        if (bicycle.getGear() != null) {
            this.gear = new GearDTO(bicycle.getGear());
        }

        if (bicycle.getWheel() != null) {
            this.wheel = new WheelDTO(bicycle.getWheel());
        }

        if (bicycle.getSaddle() != null) {
            this.saddle = new SaddleDTO(bicycle.getSaddle());
        }
    }


    public Bicycle toEntity() {
        return new Bicycle(this);
    }

    // Overrider equals til at sammenligne alle felter
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BicycleDTO that = (BicycleDTO) o;
        return id == that.id &&
                size == that.size &&
                price == that.price &&
                Double.compare(that.weight, weight) == 0 &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(model, that.model) &&
                Objects.equals(link, that.link) &&
                Objects.equals(username, that.username) &&
                Objects.equals(frame, that.frame) &&
                Objects.equals(gear, that.gear) &&
                Objects.equals(wheel, that.wheel) &&
                Objects.equals(saddle, that.saddle);
    }

    // Overrider hashCode til at inkludere alle felter
    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, size, price, weight, link, username, frame, gear, wheel, saddle);
    }
    @Override
    public String toString() {
        return "BicycleDTO{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", weight=" + weight +
                ", link='" + link + '\'' +
                ", username='" + username + '\'' +
                ", frame=" + frame +
                ", gear=" + gear +
                ", wheel=" + wheel +
                ", saddle=" + saddle +
                '}';
    }

}

