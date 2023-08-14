package com.logni.credit.service.authentication;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component 
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${loofi.auth.secretKey:authentication_Secret&KEY_WILL_BE_STORED_SSM_KMS_HSM}")
	private String secretKey;
	private final int expireTime = 1000 * 60 * 60 * 5;

	public UserDetails parseToken(String token) {
		try {
			Claims body = Jwts.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(token)
					.getBody();
			UserDetails userDetails = createUserObject(body.getSubject(), (String) body.get("role"));
			return userDetails;
		} catch (JwtException | ClassCastException e) {
			logger.error("Parse token failed", e);
			return null;
		}
	}
	UserDetails createUserObject(String userName, String roles){
		List<GrantedAuthority> authorities = new ArrayList<>();
		String[] rolesArray = gerRolesAsArray(roles);
		if (rolesArray.length > 0)
			for (String role : rolesArray) {
				authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
			}
		User user = new User(userName, "password", authorities);
		return user;
	}

	String[] gerRolesAsArray(String roles){
		return roles.split(" ");
	}
}
