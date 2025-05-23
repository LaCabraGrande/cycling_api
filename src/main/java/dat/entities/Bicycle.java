package dat.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import dat.dtos.BicycleDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bicycle")
public class Bicycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String brand;
    private String model;
    private int size;
    private int price;
    private double weight;
    private String link;
    private String username;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference  // Ejer af relationen
    private Frame frame;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference  // Ejer af relationen
    private Gear gear;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference  // Ejer af relationen
    private Wheel wheel;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference  // Ejer af relationen
    private Saddle saddle;

    // Constructor til oprettelse af ny Bicycle uden komponenter
    public Bicycle(String brand, String model, int size, int price, double weight, String link, String username) {
        this.brand = brand;
        this.model = model;
        this.size = size;
        this.price = price;
        this.weight = weight;
        this.link = link;
        this.username = username;
    }

    // Constructor til oprettelse af ny Bicycle med komponenter
    public Bicycle(String brand, String model, int size, int price, double weight, String link, String username, Frame frame, Gear gear, Wheel wheel, Saddle saddle) {
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

    // Constructor fra DTO
    public Bicycle(BicycleDTO bicycleDTO) {
        this.id = bicycleDTO.getId();
        this.brand = bicycleDTO.getBrand();
        this.model = bicycleDTO.getModel();
        this.size = bicycleDTO.getSize();
        this.price = bicycleDTO.getPrice();
        this.weight = bicycleDTO.getWeight();
        this.link = bicycleDTO.getLink();
        this.username = bicycleDTO.getUsername();
        if(bicycleDTO.getFrame() != null) {
            this.frame = bicycleDTO.getFrame().toEntity();
        }
        if(bicycleDTO.getGear() != null) {
            this.gear = bicycleDTO.getGear().toEntity();
        }
        if(bicycleDTO.getWheel() != null) {
            this.wheel = bicycleDTO.getWheel().toEntity();
        }
        if(bicycleDTO.getSaddle() != null) {
            this.saddle = bicycleDTO.getSaddle().toEntity();
        }
    }

    public void addFrame(Frame frame) {
        if (frame != null) {
            this.frame = frame;
            frame.getBicycles().add(this);
        }
    }

    public void addWheel(Wheel wheel) {
        if (wheel != null) {
            this.wheel = wheel;
            wheel.getBicycles().add(this);
        }
    }

    public void addGear(Gear gear) {
        if (gear != null) {
            this.gear = gear;
            gear.getBicycles().add(this);
        }
    }

    public void addSaddle(Saddle saddle) {
        if (saddle != null) {
            this.saddle = saddle;
            saddle.getBicycles().add(this);
        }
    }

    @Override
    public String toString() {
        return "Bicycle{" +
                "id=" + id +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", weight=" + weight +
                ", link='" + link + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

}
