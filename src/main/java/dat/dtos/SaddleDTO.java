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
    private int weight;
    private int color;

    // Hvis du ønsker at inkludere referencer til bicycles (hvis det giver mening)
    private Set<BicycleDTO> bicycles;

    // Constructor fra Saddle-entity til DTO
    public SaddleDTO(Saddle saddle) {
        this.id = saddle.getId();
        this.brand = saddle.getBrand();
        this.material = saddle.getMaterial();
        this.weight = saddle.getWeight();
        this.color = saddle.getColor();
        this.bicycles = saddle.getBicycles().stream()
                .map(BicycleDTO::new)
                .collect(Collectors.toSet());
    }
    public Saddle toEntity() {
        return new Saddle(this);
    }

}
