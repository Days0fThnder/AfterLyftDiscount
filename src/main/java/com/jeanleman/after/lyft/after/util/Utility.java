package com.jeanleman.after.lyft.after.util;

import java.text.NumberFormat;
import java.util.Locale;

import com.jeanleman.after.lyft.after.entity.Trip;

public class Utility {
	private static String APPENDER = "%20";
	public static String encodedAddress(Trip trip, boolean isStart) {
		StringBuffer formattedAddress = new StringBuffer();
		if (isStart) {
			for (String str : trip.getStartStreet().split(" ")) {
				formattedAddress.append(str).append(APPENDER);
			}
			for (String str : trip.getStartCity().split(" ")) {
				formattedAddress.append(str).append(APPENDER);
			}
			formattedAddress.append(trip.getStartState()).append(APPENDER);
			formattedAddress.append(trip.getStartZip());
			return formattedAddress.toString();

		} else {
			for (String str : trip.getEndStreet().split(" ")) {
				formattedAddress.append(str).append(APPENDER);
			}
			for (String str : trip.getEndCity().split(" ")) {
				formattedAddress.append(str).append(APPENDER);
			}
			formattedAddress.append(trip.getEndState()).append(APPENDER);
			formattedAddress.append(trip.getEndState());
			return formattedAddress.toString();
		}
	}

	public static String centToDollarConvert(long doublePayment) {
		NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
		String s = n.format(doublePayment / 100.0);
		return s;
	}

	public static long calculatePercentage(long ridePrice, long discountAmount) {
		return ridePrice - (ridePrice * discountAmount / 100);
	}
	
	public static boolean emptyTrip(Trip trip) {
		return trip.getStartStreet().isEmpty() || trip.getStartCity().isEmpty() || trip.getStartState().isEmpty()
		|| trip.getStartZip().isEmpty()  || trip.getEndStreet().isEmpty() || trip.getEndCity().isEmpty() 
		|| trip.getEndState().isEmpty()
		|| trip.getEndZip().isEmpty();
		
	}

}
