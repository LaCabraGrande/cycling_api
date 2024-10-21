package dat.dtos;

import dat.entities.Frame;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrameDTO {

    private Long id;
    private String brand;
    private String material;
    private String type;  // Hvis type er en enum, kan du ændre dette til den tilsvarende enum-type
    private int weight;

    // Hvis du ønsker at inkludere referencer til bicycles (hvis det giver mening)
    private Set<BicycleDTO> bicycles;

    // Constructor fra Frame-entity til DTO
    public FrameDTO(Frame frame) {
        this.id = frame.getId();
        this.brand = frame.getBrand();
        this.material = frame.getMaterial();
        this.type = frame.getType();
        this.weight = frame.getWeight();
        this.bicycles = frame.getBicycles().stream()
                 .map(BicycleDTO::new)
                 .collect(Collectors.toSet());
    }

    public Frame toEntity() {
        return new Frame(this);
    }

}
