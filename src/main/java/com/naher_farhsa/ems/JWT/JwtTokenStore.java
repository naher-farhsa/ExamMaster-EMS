package com.naher_farhsa.ems.JWT;


import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtTokenStore {
    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public void storeToken(String userName, String token) {
        tokenStore.put(userName, token);
    }

    public boolean isValidToken(String userName, String token) {
        return token.equals(tokenStore.get(userName));
    }

    public String getToken(String username) {
        return tokenStore.get(username);
    }

    public boolean isTokenPresent(String username) {
        return tokenStore.containsKey(username);
    }


    public boolean removeToken(String username, String token) {
        String storedToken = tokenStore.get(username);
        if (storedToken != null && storedToken.equals(token)) {
            tokenStore.remove(username);
            return true;
        }
        return false;
    }


}

