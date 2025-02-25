package com.example.dooor.jwt.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RefreshToken {

    @Id
    private Integer id;

    @Column(name = "refresh_token")
    private String refreshToken;

    public void update(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
