package com.bazzar.domain.util;

import static org.springframework.util.CollectionUtils.isEmpty;
import geo.google.GeoAddressStandardizer;
import geo.google.GeoException;
import geo.google.datamodel.GeoUsAddress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.bazzar.domain.Address;

public class GeoUtil {
	
	private static final int MAX_RETRIES = 3;
	
	@Resource
	GeoAddressStandardizer geoAddressStandardizer;
	
	@SuppressWarnings("unchecked")
    public List<Address> standardizeAddress(Address argAddress) {
    	Assert.notNull(argAddress,"address is required");
    	
    	List<Address> returnlist = Collections.EMPTY_LIST;
    	
		try {
			StringBuilder sb = new StringBuilder(argAddress.getAddressLine1());
			sb.append(",");
			
			if(argAddress.getAddressLine2() != null) {
				sb.append(argAddress.getAddressLine2());
				sb.append(",");
			}

			if(argAddress.getCity() != null) {
				sb.append(argAddress.getCity());
				sb.append(",");
			}

			if(argAddress.getState() != null) {
				sb.append(argAddress.getState());
				sb.append(",");
			}

			if(argAddress.getZip() != null) {
				sb.append(argAddress.getZip());
			}

			
			List<GeoUsAddress> addresses = null;
			int retries = 0;
			boolean retry = true;
			
			//Retry logic to handle pesky, unpredictable, and sporadic Google Exceptions
			do {
				try {
					++retries;
					addresses = geoAddressStandardizer.standardizeToGeoUsAddresses(sb.toString());
					retry = false;
				} catch (GeoException e) {
					//logger.error("Error received from Google Geocoder: " + e.getMessage(),e);
					retry = retries < MAX_RETRIES;
					if(retry) {
						//logger.info("retrying...");
					} else {
						//logger.info("exceeded retry attempts!");
					}
				}
			} while (retry);

			if(!isEmpty(addresses)) {
		    	returnlist = new ArrayList<Address>();
				
		    	for(GeoUsAddress geoUsAddress : addresses) {
		    		//Make sure enough info exists in the address
		    		if(isGeoUsAddressAdequate(geoUsAddress)) {
						Address address = new Address();
						returnlist.add(address);
						
						address.setCity(geoUsAddress.getCity());
						//address.setState(geoUsAddress.getState());
						address.setAddressLine1(geoUsAddress.getAddressLine1());
						address.setAddressLine2(geoUsAddress.getAddressLine2());
						//address.setCountry(geoUsAddress.getCountry());
						//address.setCounty(geoUsAddress.getCounty());
						address.setZip(geoUsAddress.getPostalCode());
						
						//GeoCoordinate geoCoordinate = geoUsAddress.getCoordinate();
						
//						if(geoCoordinate != null) {
//							address.setLatitude(geoCoordinate.getLatitude());
//							address.setLongitude(geoCoordinate.getLongitude());
//						}
		    		}
				}
			}
			return returnlist;
		} catch (Exception e) {
			String msg = "Unexpected error geocoding " + e.getMessage();
			//logger.error(msg,e);
			throw new RuntimeException(msg);
		}
	}
	
	/**
     * Determine if bare minimum info exists to allow user to select this as a standard address.
     * Sometimes the API is returning a bunch of empty strings
     * @param address
     * @return
     */
    private boolean isGeoUsAddressAdequate(GeoUsAddress address) {
    	boolean adequate = false;
    	
    	if(address != null) {
    		if(!StringUtils.isEmpty(address.getAddressLine1()) && 
    			!StringUtils.isEmpty(address.getCity()) &&
    			!StringUtils.isEmpty(address.getState()) &&
    			!StringUtils.isEmpty(address.getPostalCode()) &&
    			"US".equalsIgnoreCase(address.getCountry())) {
    			adequate = true;
    		}
    	}
    	
    	return adequate;
    }
}

