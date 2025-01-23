package com.example.dooor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//이미지 검증 DTO
public class VerifyImageDTO {

    @NotBlank
    private String imageUrl; // 검증할 이미지의 URL
}
