package utils;

import dto.PetDto;

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
}
