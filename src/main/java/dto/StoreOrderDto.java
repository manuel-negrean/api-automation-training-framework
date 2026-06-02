package dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder

public class StoreOrderDto {
    private long id;
    private long petId;
    private int quantity;
    private String shipdate;
    private String status;
    private boolean complete;

}