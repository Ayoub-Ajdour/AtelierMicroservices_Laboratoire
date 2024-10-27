package ma.fpl.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailService implements UserDetailsService {

    private final RestTemplate restTemplate;
    private final String gatewayUrl = "http://localhost:8889";

    public UserDetailService() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public UserDetails loadUserByUsername(String combinedUsername) throws UsernameNotFoundException {
        String[] parts = parseUsername(combinedUsername);
        String email = parts[0];
        String userType = parts[1];
        Map<String, String> userInfos = fetchUserInfo(email, userType);

        if (userInfos.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

        return createUserDetails(userInfos);
    }

    private String[] parseUsername(String combinedUsername) throws UsernameNotFoundException {
        String[] parts = combinedUsername.split(":");
        if (parts.length != 2) {
            throw new UsernameNotFoundException("Invalid username format. Expected format: email:type");
        }
        return parts;
    }

    private Map<String, String> fetchUserInfo(String email, String userType) {
        String url;
        switch (userType) {
            case "Enseignant":
                url = gatewayUrl + "/Enseignants/email/" + email;
                break;
            case "Chercheur":
                url = gatewayUrl + "/Chercheurs/email/" + email;
                break;
            default:
                return new HashMap<>();
        }
        return restTemplate.getForObject(url, HashMap.class);
    }

    private UserDetails createUserDetails(Map<String, String> userInfos) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userInfos.get("scope")));

        return new org.springframework.security.core.userdetails.User(
                userInfos.get("email"),
                "{noop}" + userInfos.get("password"),
                authorities
        );
    }
}
