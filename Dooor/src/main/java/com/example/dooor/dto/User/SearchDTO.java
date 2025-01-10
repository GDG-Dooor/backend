package com.example.dooor.dto.User;

import jakarta.validation.constraints.NotBlank;

//검색 기능을 위한 DTO로, 검색 쿼리를 포함
public class SearchDTO {

    @NotBlank
    private String query; // 검색 쿼리

    // 기본 생성자
    public SearchDTO() {}

    // Getter 및 Setter
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
