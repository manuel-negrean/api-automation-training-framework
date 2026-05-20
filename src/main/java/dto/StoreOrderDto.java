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
//    "id": 0,
//            "petId": 0,
//            "quantity": 0,
//            "shipDate": "2026-05-13T10:22:57.892Z",
//            "status": "placed",
//            "complete": true

    private long id;
    private long petId;
    private int quantity;
    private String shipDate;
    private String status;
    private boolean complete;

}
