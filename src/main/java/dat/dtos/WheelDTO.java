package dat.dtos;

import dat.entities.Wheel;
import lombok.*;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WheelDTO {

    private Long id;
    private String brand;
    private String material;
    private String type;  // Hvis type er en enum, kan du ændre dette til den tilsvarende enum-type
    private String model;
    private int weight;
    private int size;
    private String username;

    // Constructor fra Wheel-entity til DTO
    public WheelDTO(Wheel wheel) {
        this.id = wheel.getId();
        this.brand = wheel.getBrand();
        this.material = wheel.getMaterial();
        this.type = wheel.getType();
        this.model = wheel.getModel();
        this.weight = wheel.getWeight();
        this.size = wheel.getSize();
        this.username = wheel.getUsername();
    }

    public Wheel toEntity() {
        return new Wheel(this);
    }

    @Override
    public String toString() {
        return "WheelDTO{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", material='" + material + '\'' +
                ", type='" + type + '\'' +
                ", model='" + model + '\'' +
                ", weight=" + weight +
                ", size=" + size +
                ", username='" + username + '\'' +
                '}';
    }
}
