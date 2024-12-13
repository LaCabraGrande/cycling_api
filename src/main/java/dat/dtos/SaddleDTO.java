package dat.dtos;

import dat.entities.Saddle;
import lombok.*;
import java.util.stream.Collectors;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaddleDTO {

    private Long id;
    private String brand;
    private String material;
    private String model;
    private int weight;
    private int width;


    // Constructor fra Saddle-entity til DTO
    public SaddleDTO(Saddle saddle) {
        this.id = saddle.getId();
        this.brand = saddle.getBrand();
        this.material = saddle.getMaterial();
        this.model = saddle.getModel();
        this.weight = saddle.getWeight();
        this.width = saddle.getWidth();
    }

    public Saddle toEntity() {
        return new Saddle(this);
    }

    @Override
    public String toString() {
        return "SaddleDTO{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", material='" + material + '\'' +
                ", model='" + model + '\'' +
                ", weight=" + weight +
                ", width=" + width +
                '}';
    }

}
