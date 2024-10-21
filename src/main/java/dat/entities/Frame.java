package dat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.dtos.FrameDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "frame")
public class Frame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = true)
    private String brand;

    @Column(name = "material", nullable = true)
    private String material;

    // her tænker jeg at type er en enum som kunne være om den er sat op til disc eller bremser
    @Column(name = "type", nullable = true)
    private String type;

    @Column(name = "weight", nullable = true)
    private int weight;

    @OneToMany(mappedBy = "frame")
    @JsonIgnore
    @ToString.Exclude
    @JsonBackReference
    private Set<Bicycle> bicycles;

    public Frame(String brand, String material, String type, int weight) {
        this.brand = brand;
        this.material = material;
        this.type = type;
        this.weight = weight;
    }

    public Frame(FrameDTO frameDTO) {
        this.id = frameDTO.getId();
        this.brand = frameDTO.getBrand();
        this.material = frameDTO.getMaterial();
        this.type = frameDTO.getType();
        this.weight = frameDTO.getWeight();
    }

    public void addBicycle(Bicycle bicycle) {
        this.bicycles.add(bicycle);
    }
}
