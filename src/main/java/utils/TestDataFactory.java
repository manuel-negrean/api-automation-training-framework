package utils;

import dto.PetDto;
import dto.StoreOrderDto;

import java.util.Collections;
import java.util.List;

public class TestDataFactory {
    public static PetDto createPet(long id, String petName,PetDto.CategoryDto category, PetDto.TagDto tag, String status) {
        PetDto petDto = new PetDto();
        petDto.setId(id);
        petDto.setName(petName);
        petDto.setCategory(category);
        petDto.setTags(Collections.singletonList(tag));
        petDto.setStatus(status);
        petDto.setPhotoUrls(Collections.emptyList());
        return petDto;
    }
    public static StoreOrderDto createStoreOrder(long id, long petId, int quantity, String shipDate, String status, boolean complete) {
        StoreOrderDto orderDto = new StoreOrderDto();
        orderDto.setId(id);
        orderDto.setPetId(petId);
        orderDto.setQuantity(quantity);
        orderDto.setShipDate(shipDate);
        orderDto.setStatus(status);
        orderDto.setComplete(complete);
        return orderDto;
    }
}
