package dat.dtos;

import dat.entities.Frame;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrameDTO {

    private Long id;
    private String brand;
    private String model;
    private String material;
    private String type;
    private int weight;
    private int size;
    private String username;


    // Constructor fra Frame-entity til DTO
    public FrameDTO(Frame frame) {
        this.id = frame.getId();
        this.brand = frame.getBrand();
        this.model = frame.getModel();
        this.material = frame.getMaterial();
        this.type = frame.getType();
        this.weight = frame.getWeight();
        this.size = frame.getSize();
        this.username = frame.getUsername();
    }

    public Frame toEntity() {
        return new Frame(this);
    }

}
