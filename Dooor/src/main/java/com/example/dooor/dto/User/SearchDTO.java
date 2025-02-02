package com.example.dooor.dto.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
//검색 기능을 위한 DTO로, 검색 쿼리를 포함
public class SearchDTO {

    @NotBlank
    private String query; // 검색 쿼리
}
