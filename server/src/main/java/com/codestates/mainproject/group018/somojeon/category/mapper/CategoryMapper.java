package com.codestates.mainproject.group018.somojeon.category.mapper;

import com.codestates.mainproject.group018.somojeon.category.dto.CategoryDto;
import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryDto.Response categoryToCategoryResponseDto(Category category);

    List<CategoryDto.Response> categoryToCategoryResponseDtos(List<Category> categories);


}
