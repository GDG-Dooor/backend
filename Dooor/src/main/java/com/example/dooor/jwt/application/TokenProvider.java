package com.example.dooor.jwt.application;

import com.example.dooor.jwt.domain.RefreshToken;
import com.example.dooor.jwt.domain.repository.RefreshTokenRepository;
import com.example.dooor.user.domain.User;
import com.example.dooor.jwt.api.dto.response.AccessTokenRes;
import com.example.dooor.user.domain.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private static final String ROLE_CLAIM = "Role";
    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";

    private final Key key;
    private final long accessTokenValidityTime;
    private final long refreshTokenValidityTime;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenProvider(@Value("${jwt.secret}") String secretKey,
                         @Value("${jwt.access-token-validity-in-milliseconds}") long accessTokenValidityTime,
                         @Value("${jwt.refresh-token-validity-in-milliseconds}") long refreshTokenValidityTime, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidityTime = accessTokenValidityTime;
        this.refreshTokenValidityTime = refreshTokenValidityTime;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createAccessToken(User user) {
        Date date = new Date();

        Date accessTokenExpiredTime = new Date(date.getTime() + accessTokenValidityTime);

        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .claim(ROLE_CLAIM, user.getRole().name())
                .setIssuedAt(date)
                .setExpiration(accessTokenExpiredTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(User user) {
        Date date = new Date();

        Date refreshTokenExpiredTime = new Date(date.getTime() + refreshTokenValidityTime);

        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .setExpiration(refreshTokenExpiredTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(ROLE_CLAIM) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 사용자의 권한 정보를 securityContextHolder에 담아준다
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(ROLE_CLAIM).toString().split(","))
                        // 해당 hasRole이 권한 정보를 식별하기 위한 전처리 작업
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
        authentication.setDetails(claims);

        return authentication;
    }

    public String resolveToken(HttpServletRequest request) { //토큰 분해/분석
        String bearerToken = request.getHeader(AUTHORIZATION);

        System.out.println(bearerToken);
        System.out.println(bearerToken.substring(7).trim());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에 대한 처리
            System.out.println("토큰이 만료되었습니다: " + e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            // 지원하지 않는 토큰에 대한 처리
            System.out.println("지원하지 않는 토큰입니다: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            // 토큰이 잘못된 경우 처리
            System.out.println("토큰이 잘못되었습니다: " + e.getMessage());
            return false;
        } catch (SignatureException e) {
            // 서명 오류 처리
            System.out.println("토큰 서명에 실패했습니다: " + e.getMessage());
            return false;
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (SignatureException e) {
            throw new RuntimeException("토큰 복호화에 실패했습니다.");
        }
    }

    public AccessTokenRes reissueAccessToken(HttpServletRequest request) {
        String token = resolveToken(request);

        Integer userId = Integer.parseInt(parseClaims(token).getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저 없음"));
        RefreshToken refreshToken = refreshTokenRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("토큰 없음"));

        if(token.equals(refreshToken.getRefreshToken())){
            throw new RuntimeException("리프레쉬 토큰이 다름");
        }

        return AccessTokenRes.builder()
                .accessToken(createAccessToken(user))
                .build();
    }
    public void revokeAccessToken(String accessToken) {
        // 블랙리스트에 엑세스 토큰 추가하는 로직
        // 예: Redis, 데이터베이스 또는 메모리에 저장
        System.out.println("엑세스 토큰이 무효화되었습니다: " + accessToken);
    }
}
