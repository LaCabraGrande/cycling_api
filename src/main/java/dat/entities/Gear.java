package dat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.dtos.GearDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "gear")
public class Gear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = true)
    private String brand;

    @Column(name = "model", nullable = true)
    private String model;

    @Column(name = "series", nullable = true)
    private String series;

    @Column(name = "material", nullable = true)
    private String material;

    @Column(name = "type", nullable = true)
    private String type;

    @Column(name = "braketype", nullable = true)
    private String brakes;

    @Column(name = "weight", nullable = true)
    private int weight;


    @OneToMany(mappedBy = "gear")
    @JsonIgnore
    @ToString.Exclude
    @JsonBackReference
    private Set<Bicycle> bicycles;

    public Gear(String brand, String model, String series, String material, String type, String brakes, int weight) {
        this.brand = brand;
        this.model = model;
        this.series = series;
        this.material = material;
        this.type = type;
        this.brakes = brakes;
        this.weight = weight;
    }

    public Gear(GearDTO gearDTO) {
        this.id = gearDTO.getId();
        this.brand = gearDTO.getBrand();
        this.series = gearDTO.getSeries();
        this.model = gearDTO.getModel();
        this.material = gearDTO.getMaterial();
        this.type = gearDTO.getType();
        this.brakes = gearDTO.getBrakes();
        this.weight = gearDTO.getWeight();
    }

    public void addBicycle(Bicycle bicycle) {
        this.bicycles.add(bicycle);
    }

    @Override
    public String toString() {
        return "Gear{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", series='" + series + '\'' +
                ", material='" + material + '\'' +
                ", type='" + type + '\'' +
                ", brakes='" + brakes + '\'' +
                ", weight=" + weight +
                '}';
    }
}
