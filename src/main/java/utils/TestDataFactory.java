package utils;

import dto.PetDto;
import dto.StoreOrderDto;

import java.util.Collections;
import java.util.List;

public class TestDataFactory {
    public static PetDto createPet(long id,  String petName, PetDto.CategoryDto categoryName, String status, PetDto.TagDto petTag) {
        PetDto petDto = new PetDto();
        petDto.setId(id);
        petDto.setName(petName);
        petDto.setCategory(categoryName);
        petDto.setStatus(status);
        petDto.setTags(Collections.singletonList(petTag));
        petDto.setPhotoUrls(Collections.emptyList());
        return petDto;
    }

    public static PetDto FindPetsById (long id) {
        PetDto petDto = new PetDto();
        petDto.setId(id);
        return petDto;
    }

    public static PetDto updatePet(long id, PetDto.CategoryDto categoryName, String petName, PetDto.TagDto petTag, String status) {
        PetDto petDto = new PetDto();
        petDto.setId(id);
        petDto.setCategory(categoryName);
        petDto.setName(petName);
        petDto.setTags(Collections.singletonList(petTag));
        petDto.setStatus(status);
        petDto.setPhotoUrls(Collections.emptyList());
        return petDto;


        }

    public static StoreOrderDto createOrder(long id, long petId, int quantity, String shipDate, String status, boolean complete) {
        StoreOrderDto storeOrderDto = new StoreOrderDto();
        storeOrderDto.setId(id);
        storeOrderDto.setPetId(petId);
        storeOrderDto.setQuantity(quantity);
        storeOrderDto.setShipDate(shipDate);
        storeOrderDto.setStatus(status);
        storeOrderDto.setComplete(complete);
        return storeOrderDto;
    }}
