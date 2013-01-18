package com.bazzar.service;

import java.util.List;

import com.bazzar.domain.lookup.StateTypeLookup;

public interface GeoService {

	public List<String> findZipCodesInRadius(String zip, int miles);
	public List<String> findZipCodesInRadius(String city, StateTypeLookup state, int miles);
}
