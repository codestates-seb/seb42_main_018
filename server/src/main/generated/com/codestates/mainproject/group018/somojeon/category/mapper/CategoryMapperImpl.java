package com.codestates.mainproject.group018.somojeon.category.mapper;

import com.codestates.mainproject.group018.somojeon.category.dto.CategoryDto;
import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-13T00:17:10+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category categoryPostDtoToCategory(CategoryDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Category category = new Category();

        category.setCategoryName( requestBody.getCategoryName() );

        return category;
    }

    @Override
    public CategoryDto.Response categoryResponseDtoToCategory(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto.Response response = new CategoryDto.Response();

        return response;
    }

    @Override
    public List<CategoryDto.Response> categoryResponseDtosToCategory(List<Category> categoryList) {
        if ( categoryList == null ) {
            return null;
        }

        List<CategoryDto.Response> list = new ArrayList<CategoryDto.Response>( categoryList.size() );
        for ( Category category : categoryList ) {
            list.add( categoryResponseDtoToCategory( category ) );
        }

        return list;
    }
}
