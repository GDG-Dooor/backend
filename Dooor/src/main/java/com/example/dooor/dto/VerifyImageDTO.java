package com.example.dooor.dto;

import jakarta.validation.constraints.NotBlank;
//이미지 검증 DTO
public class VerifyImageDTO {

    @NotBlank
    private String imageUrl; // 검증할 이미지의 URL

    // 기본 생성자
    public VerifyImageDTO() {}

    // Getter 및 Setter
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
