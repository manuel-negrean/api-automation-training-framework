package utils;

import dto.PetDto;
import dto.PetDto.CategoryDto;
import dto.PetDto.TagDto;
import java.util.Collections;

public class TestDataFactory {

    public static PetDto createPet(long id,
                                   String petName,
                                   CategoryDto category,
                                   TagDto petTag,
                                   String petStatus) {
        PetDto petDto = new PetDto();
        petDto.setId(id);
        petDto.setName(petName);
        petDto.setCategory(category);
        petDto.setTags(Collections.singletonList(petTag));
        petDto.setStatus(petStatus);
        petDto.setPhotoUrls(Collections.emptyList());
        return petDto;
    }
}
