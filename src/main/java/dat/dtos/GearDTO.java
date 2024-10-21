package dat.dtos;

import dat.entities.Gear;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GearDTO {

    private Long id;
    private String brand;
    private String material;
    private String type;
    private int weight;
    private int color;

    // Hvis du ønsker at inkludere referencer til bicycles (hvis det giver mening)
    private Set<BicycleDTO> bicycles;

    // Constructor fra Gear-entity til DTO
    public GearDTO(Gear gear) {
        this.id = gear.getId();
        this.brand = gear.getBrand();
        this.material = gear.getMaterial();
        this.type = gear.getType();
        this.weight = gear.getWeight();
        this.color = gear.getColor();
        this.bicycles = gear.getBicycles().stream()
                 .map(BicycleDTO::new)
                 .collect(Collectors.toSet());
    }

    public Gear toEntity() {
        return new Gear(this);
    }

}
