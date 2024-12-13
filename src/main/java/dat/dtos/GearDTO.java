package dat.dtos;

import dat.entities.Gear;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GearDTO {

    private Long id;
    private String brand;
    private String model;
    private String series;
    private String material;
    private String type;
    private String brakes;
    private int weight;

    // Constructor fra Gear-entity til DTO
    public GearDTO(Gear gear) {
        this.id = gear.getId();
        this.brand = gear.getBrand();
        this.model = gear.getModel();
        this.series = gear.getSeries();
        this.material = gear.getMaterial();
        this.type = gear.getType();
        this.brakes = gear.getBrakes();
        this.weight = gear.getWeight();
    }



    public Gear toEntity() {
        return new Gear(this);
    }

    @Override
    public String toString() {
        return "GearDTO{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", series='" + series + '\'' +
                ", material='" + material + '\'' +
                ", type='" + type + '\'' +
                ", brakes='" + brakes + '\'' +
                ", weight=" + weight +
                '}';
    }

}
