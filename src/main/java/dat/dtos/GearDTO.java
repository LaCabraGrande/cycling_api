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
    private String model;
    private String material;
    private String type;
    private String brakes;
    private int weight;

    // Constructor fra Gear-entity til DTO
    public GearDTO(Gear gear) {
        this.id = gear.getId();
        this.brand = gear.getBrand();
        this.model = gear.getModel();
        this.material = gear.getMaterial();
        this.type = gear.getType();
        this.brakes = gear.getBrakes();
        this.weight = gear.getWeight();
    }



    public Gear toEntity() {
        return new Gear(this);
    }

}
