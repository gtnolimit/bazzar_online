package com.bazzar.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bazzar.dao.GeoZipDao;
import com.bazzar.domain.GeoZip;
import com.bazzar.domain.lookup.StateTypeLookup;
import com.bazzar.service.GeoService;

@Service("geoService")
public class GeoServiceImpl implements GeoService {

	@Resource
	GeoZipDao geoZipDao;
	
	public List<String> findZipCodesInRadius(String zip, int miles) {
		GeoZip zipInfo = geoZipDao.getZipInfo(zip);
		double longitude = zipInfo.getLongitude();
		double latitude = zipInfo.getLatitude();
		
		double minLongitude = longitude, maxLongitude = longitude;
		double minLatitude = latitude, maxLatitude = latitude;
		double gradPerMiles = ((double)miles)/69.09;
		minLongitude = longitude - gradPerMiles;
		maxLongitude = longitude + gradPerMiles;
		minLatitude = latitude - gradPerMiles;
		maxLatitude = latitude + gradPerMiles;
		
		if (minLongitude > maxLongitude) {
			double tmp = minLongitude;
			minLongitude = maxLongitude;
			maxLongitude = tmp;
		}
		
		if (minLatitude > maxLatitude) {
			double tmp = minLatitude;
			minLatitude = maxLatitude;
			maxLatitude = tmp;
		}
		
		List<GeoZip> zipSquare = geoZipDao.getZips(minLatitude, maxLatitude, minLongitude, maxLongitude);
		return getZipsInRadius(zipInfo, zipSquare, miles);
	}
	
	public List<String> findZipCodesInRadius(String city, StateTypeLookup state, int miles) {
		List<GeoZip> zipCity = geoZipDao.getZips(city, state);
		if (zipCity==null || zipCity.size()==0)
			return new LinkedList<String>();
		GeoZip zipInfo = zipCity.get(0);
		
		double longitude = zipInfo.getLongitude();
		double latitude = zipInfo.getLatitude();
		
		double minLongitude = longitude, maxLongitude = longitude;
		double minLatitude = latitude, maxLatitude = latitude;
		double gradPerMiles = ((double)miles)/69.09;
		minLongitude = longitude - gradPerMiles;
		maxLongitude = longitude + gradPerMiles;
		minLatitude = latitude - gradPerMiles;
		maxLatitude = latitude + gradPerMiles;
		
		if (minLongitude > maxLongitude) {
			double tmp = minLongitude;
			minLongitude = maxLongitude;
			maxLongitude = tmp;
		}
		
		if (minLatitude > maxLatitude) {
			double tmp = minLatitude;
			minLatitude = maxLatitude;
			maxLatitude = tmp;
		}
		
		List<GeoZip> zipSquare = geoZipDao.getZips(minLatitude, maxLatitude, minLongitude, maxLongitude);
		return getZipsInRadius(zipInfo, zipSquare, miles);
	}
	
	private List<String> getZipsInRadius(GeoZip zipInfo, List<GeoZip> zipSquare, int radius) {
		List<String> results = new LinkedList<String>();
		for (GeoZip zip : zipSquare) {
			if (!zip.getZip().equals(zipInfo.getZip())) {
				double distance = calcDistance(zipInfo.getLatitude(),zipInfo.getLongitude(),
												zip.getLatitude(),zip.getLongitude());
				if (distance<=radius)
					results.add(zip.getZip());
			}
		}
		return results;
	}
	
	private double calcDistance(double latA, double longA, double latB, double longB)
	{
	  double theDistance = (Math.sin(Math.toRadians(latA)) *
	                        Math.sin(Math.toRadians(latB)) +
	                        Math.cos(Math.toRadians(latA)) *
	                        Math.cos(Math.toRadians(latB)) *
	                        Math.cos(Math.toRadians(longA - longB)));

	  return (Math.toDegrees(Math.acos(theDistance))) * 69.09;
	}

}
