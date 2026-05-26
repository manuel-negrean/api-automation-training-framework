package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreOrderDto {
    private long id;
    private long petId;
    private int quantity;
    private String shipDate; // ISO 8601 format
    private String status; // e.g., "placed", "approved", "delivered"
    private boolean complete;
}


