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

    @Column(name = "material", nullable = true)
    private String material;

    // her tænker jeg at type er en enum som kunne være elektronisk eller mekanisk
    @Column(name = "type", nullable = true)
    private String type;

    @Column(name = "weight", nullable = true)
    private int weight;


    @OneToMany(mappedBy = "gear")
    @JsonIgnore
    @ToString.Exclude
    @JsonBackReference
    private Set<Bicycle> bicycles;

    public Gear(String brand, String model, String material, String type, int weight) {
        this.brand = brand;
        this.model = model;
        this.material = material;
        this.type = type;
        this.weight = weight;
    }

    public Gear(GearDTO gearDTO) {
        this.id = gearDTO.getId();
        this.brand = gearDTO.getBrand();
        this.model = gearDTO.getModel();
        this.material = gearDTO.getMaterial();
        this.type = gearDTO.getType();
        this.weight = gearDTO.getWeight();
    }

    public void addBicycle(Bicycle bicycle) {
        this.bicycles.add(bicycle);
    }
}
