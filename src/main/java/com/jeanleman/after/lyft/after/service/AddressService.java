package com.jeanleman.after.lyft.after.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jeanleman.after.lyft.after.entity.Coordinate;
import com.jeanleman.after.lyft.after.entity.Trip;
import com.jeanleman.after.lyft.after.util.Utility;

@Component
public class AddressService {

	public Coordinate getCoordinatesFromAddress(Trip trip, boolean isStartAddress)
			throws URISyntaxException, JSONException {

		String url = "https://api.opencagedata.com/geocode/v1/json?" + "q=" + Utility.encodedAddress(trip, isStartAddress)
				+ "&key=c769240df7a744dc9cf4c566faa04049&language=en&pretty=1";
		URI uri = new URI(url);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		String address = result.getBody();
		JSONObject res = new JSONObject(address);

		if(res.has("results")){
			JSONArray results = res.getJSONArray("results");
			if(!results.isEmpty()){
				JSONObject goeData = results.getJSONObject(0).getJSONObject("geometry");
				double lat = goeData.getDouble("lat");
				double lng = goeData.getDouble("lng");
				return new Coordinate(lat, lng);
			}
		}
		JSONObject loc = res.getJSONObject("Response").getJSONArray("View").getJSONObject(0).getJSONArray("Result")
				.getJSONObject(0).getJSONObject("Location").getJSONArray("NavigationPosition").getJSONObject(0);
		double lat = loc.getDouble("Latitude");
		double lng = loc.getDouble("Longitude");
		return new Coordinate(lat, lng);

	}

}
