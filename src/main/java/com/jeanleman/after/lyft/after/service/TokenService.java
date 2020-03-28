package com.jeanleman.after.lyft.after.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.jeanleman.after.lyft.after.entity.Token;
import com.jeanleman.after.lyft.after.util.AfterHttp;

@Component
public class TokenService {
	
	public Token getToken() throws JSONException, URISyntaxException {
		final String tokenUrl = "https://api.lyft.com/oauth/token";
		URI uri = new URI(tokenUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
		map.add("grant_type", "client_credentials");
		map.add("scope", "public");

		RestTemplate restTemplate = new RestTemplate(AfterHttp.getClientHttpRequestFactory());
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
		
		String resp = result.getBody().toString();
		JSONObject res = new JSONObject(resp);
		Token token = new Token(res.getString("scope"));
		token.setToken_type(res.getString("token_type"));
		token.setAccess_token(res.getString("access_token"));
		token.setExpires_in(res.getInt("expires_in"));
		
		
		
		return token;
	}

}
