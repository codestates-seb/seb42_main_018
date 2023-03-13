package com.codestates.mainproject.group018.somojeon.category.entity;

import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false)
    // 리스트로 미리 넣어놓는다.
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Club> clubList = new ArrayList<>();

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
