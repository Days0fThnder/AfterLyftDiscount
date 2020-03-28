package com.jeanleman.after.lyft.after.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jeanleman.after.lyft.after.entity.Coordinate;
import com.jeanleman.after.lyft.after.entity.Token;
import com.jeanleman.after.lyft.after.util.Constants;
import com.jeanleman.after.lyft.after.util.Utility;

@Component
public class EstimateService {
	
	public String getRideCostEstimate(Coordinate starting, Coordinate ending, Token token) throws URISyntaxException, JSONException {
		String url = "https://api.lyft.com/v1/cost?start_lat="+starting.getLat()+
				"&start_lng="+starting.getLng()+"&end_lat="+ending.getLat()+"&end_lng="+
				ending.getLng()+"&ride_type=lyft";
		URI uri = new URI(url);
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token.getAccess_token());
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		
		String costEstimate = result.getBody().toString();
		JSONObject res = new JSONObject(costEstimate);
		
		// check to see if the estimated cost object has any errors
		// if so return the error message
		try {
			return res.getJSONArray("cost_estimates").getJSONObject(0).getString("error_message");
		}catch(JSONException ex) {
			System.out.print(ex.getMessage());
		}
		
		long ridePrice = res.getJSONArray("cost_estimates").getJSONObject(0).getInt("estimated_cost_cents_max");
		
		return Utility.centToDollarConvert(Utility.calculatePercentage(ridePrice, Constants.DISCOUNT));
		
	}

}
