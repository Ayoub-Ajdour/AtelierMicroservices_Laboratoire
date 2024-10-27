package ma.fpl.security.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class Oauth2Controller {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserDetailsService userDetailsService;

    public Oauth2Controller(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public Map<String, String> login(String username, String password, String userType) {
        String combined = username + ":" + userType;
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(combined, password));
        Instant instant = Instant.now();
        String scope = authenticate.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));

        JwtClaimsSet accessTokenClaims = JwtClaimsSet.builder()
                .issuer("MS_sec")
                .subject(authenticate.getName())
                .issuedAt(instant)
                .expiresAt(instant.plus(2, ChronoUnit.MINUTES))
                .claim("name", authenticate.getName())
                .claim("scope", scope)
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(accessTokenClaims)).getTokenValue();

        JwtClaimsSet refreshTokenClaims = JwtClaimsSet.builder()
                .issuer("MS_sec")
                .subject(authenticate.getName())
                .issuedAt(instant)
                .expiresAt(instant.plus(15, ChronoUnit.MINUTES))
                .claim("name", authenticate.getName())
                .build();

        String refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(refreshTokenClaims)).getTokenValue();

        Map<String, String> tokens = new HashMap<>();
        tokens.put("Access_Token", accessToken);
        tokens.put("Refresh_Token", refreshToken);

        return tokens;
    }

    @PostMapping("/RefreshToken")
    public Map<String, String> refreshToken(String refreshToken, String userType) {
        if (refreshToken == null) {
            return Map.of("Message error", "Refresh_Token is required");
        }

        Jwt decoded = jwtDecoder.decode(refreshToken);
        String username = decoded.getSubject();
        String combined = username + ":" + userType;

        UserDetails userDetails = userDetailsService.loadUserByUsername(combined);
        Instant instant = Instant.now();
        String scope = userDetails.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));

        JwtClaimsSet accessTokenClaims = JwtClaimsSet.builder()
                .issuer("MS_sec")
                .subject(userDetails.getUsername())
                .issuedAt(instant)
                .expiresAt(instant.plus(2, ChronoUnit.MINUTES))
                .claim("name", userDetails.getUsername())
                .claim("scope", scope)
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(accessTokenClaims)).getTokenValue();

        Map<String, String> tokens = new HashMap<>();
        tokens.put("Access_Token", accessToken);
        tokens.put("Refresh_Token", refreshToken);

        return tokens;
    }
}
