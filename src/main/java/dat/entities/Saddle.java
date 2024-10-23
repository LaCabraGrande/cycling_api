package dat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.dtos.SaddleDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "saddle")
public class Saddle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = true)
    private String brand;

    @Column(name = "type", nullable = true)
    private String material;

    @Column(name = "model", nullable = true)
    private String model;

    @Column(name = "weight", nullable = true)
    private int weight;

    @Column(name = "width", nullable = true)
    private int width;

    @OneToMany(mappedBy = "saddle")
    @JsonIgnore
    @ToString.Exclude
    @JsonBackReference
    private Set<Bicycle> bicycles;

    public Saddle(String brand, String material, String model, int weight, int width) {
        this.brand = brand;
        this.material = material;
        this.model = model;
        this.weight = weight;
        this.width = width;
    }

    public Saddle(SaddleDTO saddleDTO) {
        this.id = saddleDTO.getId();
        this.brand = saddleDTO.getBrand();
        this.material = saddleDTO.getMaterial();
        this.model = saddleDTO.getModel();
        this.weight = saddleDTO.getWeight();
        this.width = saddleDTO.getWidth();
    }

    public void addBicycle(Bicycle bicycle) {
        this.bicycles.add(bicycle);
    }
}
