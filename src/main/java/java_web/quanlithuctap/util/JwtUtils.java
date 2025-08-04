package java_web.quanlithuctap.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.accessExpirationMs}")
    private long jwtExpirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateAccessToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", List.of("ROLE_" + role.toUpperCase()));

        return Jwts.builder()
                .setSubject(username)
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Claims claims = parseClaims(token);
        Object authorities = claims.get("authorities");

        if (authorities instanceof List<?>) {
            return ((List<?>) authorities)
                    .stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token hết hạn");
        } catch (UnsupportedJwtException e) {
            System.out.println("Token không hỗ trợ");
        } catch (MalformedJwtException e) {
            System.out.println("Token không hợp lệ");
        } catch (SignatureException e) {
            System.out.println("Chữ ký không khớp");
        } catch (IllegalArgumentException e) {
            System.out.println("Token rỗng hoặc sai");
        }
        return false;
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
