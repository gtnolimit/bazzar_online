package com.bazzar.dao;

import java.util.List;

import com.bazzar.domain.GeoZip;
import com.bazzar.domain.lookup.StateTypeLookup;

public interface GeoZipDao {

	public GeoZip getZipInfo(String zip);
	public List<GeoZip> getZips(String city, StateTypeLookup state);
	public List<GeoZip> getZips(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude);
}
