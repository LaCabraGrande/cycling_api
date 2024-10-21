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
    private String size;
    private int price;
    private String description;

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
    public Bicycle(String brand, String model, String size, int price, String description) {
        this.brand = brand;
        this.model = model;
        this.size = size;
        this.price = price;
        this.description = description;
    }

    // Constructor til oprettelse af ny Bicycle med komponenter
    public Bicycle(String brand, String model, String size, int price, String description, Frame frame, Gear gear, Wheel wheel, Saddle saddle) {
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

    // Constructor fra DTO
    public Bicycle(BicycleDTO bicycleDTO) {
        this.id = bicycleDTO.getId();
        this.brand = bicycleDTO.getBrand();
        this.model = bicycleDTO.getModel();
        this.size = bicycleDTO.getSize();
        this.price = bicycleDTO.getPrice();
        this.description = bicycleDTO.getDescription();
        this.frame = bicycleDTO.getFrame().toEntity();
        this.gear = bicycleDTO.getGear().toEntity();
        this.wheel = bicycleDTO.getWheel().toEntity();
        this.saddle = bicycleDTO.getSaddle().toEntity();
    }
}
