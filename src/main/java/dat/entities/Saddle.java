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
@Table(name = "frame")
public class Saddle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = true)
    private String brand;

    @Column(name = "type", nullable = true)
    private String material;

    @Column(name = "weight", nullable = true)
    private int weight;

    @Column(name = "color", nullable = true)
    private int color;

    @OneToMany(mappedBy = "frame")
    @JsonIgnore
    @ToString.Exclude
    @JsonBackReference
    private Set<Bicycle> bicycles;

    public Saddle(SaddleDTO saddleDTO) {
        this.id = saddleDTO.getId();
        this.brand = saddleDTO.getBrand();
        this.material = saddleDTO.getMaterial();
        this.weight = saddleDTO.getWeight();
        this.color = saddleDTO.getColor();
    }

    public void addBicycle(Bicycle bicycle) {
        this.bicycles.add(bicycle);
    }
}
