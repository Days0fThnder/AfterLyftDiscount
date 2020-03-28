package com.jeanleman.after.lyft.after.rest;

import java.net.URISyntaxException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeanleman.after.lyft.after.entity.Coordinate;
import com.jeanleman.after.lyft.after.entity.Token;
import com.jeanleman.after.lyft.after.entity.Trip;
import com.jeanleman.after.lyft.after.service.AddressService;
import com.jeanleman.after.lyft.after.service.EstimateService;
import com.jeanleman.after.lyft.after.service.TokenService;
import com.jeanleman.after.lyft.after.util.Utility;

@Controller
@RequestMapping("/afterLyft")
public class AfterLyftDiscountController {
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private EstimateService estimateService;
	
	Token token;
	@GetMapping("/home")
	public String afterLyftHome(@ModelAttribute("trip") Trip trip) {
		
		return "home";
	}
	
	@PostMapping("/discount")
	public String getAfterLyftDiscount(@ModelAttribute("trip") Trip trip, Model theModel) throws URISyntaxException, JSONException {
		if(Utility.emptyTrip(trip)) {
			return "redirect:/afterLyft/home";
		}
		Coordinate startCoors = addressService.getCoordinatesFromAddress(trip, true);
		
		Coordinate endCoors = addressService.getCoordinatesFromAddress(trip, false);
		
		if(token == null)
			token = tokenService.getToken();
		String estCost = estimateService.getRideCostEstimate(startCoors, endCoors, token);
		theModel.addAttribute("estCost", estCost);
		return "discountInfo";
	}
	
}
