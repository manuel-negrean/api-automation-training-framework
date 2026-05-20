package utils;

import dto.PetDto;
import dto.StoreOrderDto;

import java.util.Collections;

public class TestDataFactory {
    public static PetDto createPet(long id, String petName, PetDto.CategoryDto categoryName, PetDto.TagDto petTag, String petStatus) {
        PetDto petDto = new PetDto();
        petDto.setId(id);
        petDto.setName(petName);
        petDto.setCategory(categoryName);
        petDto.setTags(Collections.singletonList(petTag));
        petDto.setStatus(petStatus);
        petDto.setPhotoUrls(Collections.emptyList()); // Assuming no photos for simplicity
        return petDto;
    }

    public static StoreOrderDto createOrder(long id, int petID, int quantity, String status, String placed, boolean completed) {
        StoreOrderDto storeOrderDto = new StoreOrderDto();
        storeOrderDto.setId(id);
        storeOrderDto.setPetId(petID);
        storeOrderDto.setQuantity(quantity);
        storeOrderDto.setStatus(status);
        storeOrderDto.setComplete(completed);
        return storeOrderDto;


    }
}
