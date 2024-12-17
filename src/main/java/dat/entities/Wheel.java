package dat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.dtos.WheelDTO;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "wheel")
public class Wheel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = true)
    private String brand;

    @Column(name = "material", nullable = true)
    private String material;

    @Column(name = "type", nullable = true)
    private String type;

    @Column(name = "model", nullable = true)
    private String model;

    @Column(name = "weight", nullable = true)
    private int weight;

    @Column(name = "size", nullable = true)
    private int size;

    @Column(name = "username", nullable = true)
    private String username;

    @OneToMany(mappedBy = "wheel")
    @JsonIgnore
    @ToString.Exclude
    @JsonBackReference
    private Set<Bicycle> bicycles;

    public Wheel(String brand, String material, String type, String model, int weight, int size, String username) {
        this.brand = brand;
        this.material = material;
        this.type = type;
        this.model = model;
        this.weight = weight;
        this.size = size;
        this.username = username;
    }

    public Wheel(WheelDTO wheelDTO) {
        this.id = wheelDTO.getId();
        this.brand = wheelDTO.getBrand();
        this.material = wheelDTO.getMaterial();
        this.type = wheelDTO.getType();
        this.model = wheelDTO.getModel();
        this.weight = wheelDTO.getWeight();
        this.size = wheelDTO.getSize();
        this.username = wheelDTO.getUsername();
    }

    public void addBicycle(Bicycle bicycle) {
        this.bicycles.add(bicycle);
    }
}
