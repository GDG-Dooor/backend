package com.example.dooor.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AwsS3DTO {
    private String fileName;
    private String fileUrl;
}
