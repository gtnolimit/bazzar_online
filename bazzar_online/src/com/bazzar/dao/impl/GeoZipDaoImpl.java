package com.bazzar.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bazzar.dao.GeoZipDao;
import com.bazzar.domain.GeoZip;
import com.bazzar.domain.lookup.StateTypeLookup;

@Repository("geoZipDao")
public class GeoZipDaoImpl extends BaseDaoImpl implements GeoZipDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public GeoZip getZipInfo(String zip) {
		Query query = (Query) sessionFactory.getCurrentSession ( )
        	.createQuery("select f from GeoZip f WHERE f.zip = :zip");
			query.setParameter("zip", zip);
		return (GeoZip) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<GeoZip> getZips(String city, StateTypeLookup state) {
		Query query = (Query) sessionFactory.getCurrentSession ( )
	    	.createQuery("select f from GeoZip f " + 
	    					"WHERE f.city=:city " +
	    					"AND f.state=:state");
		query.setParameter("city", city);
		query.setParameter("state", state);
		return (List<GeoZip>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<GeoZip> getZips(double minLatitude, double maxLatitude,
			double minLongitude, double maxLongitude) {
		Query query = (Query) sessionFactory.getCurrentSession ( )
	    	.createQuery("select f from GeoZip f " + 
	    					"WHERE f.latitude between :minLatitude and :maxLatitude " +
	    					"AND f.longitude between :minLongitude and :maxLongitude");
		query.setParameter("minLatitude", minLatitude);
		query.setParameter("maxLatitude", maxLatitude);
		query.setParameter("minLongitude", minLongitude);
		query.setParameter("maxLongitude", maxLongitude);
		return (List<GeoZip>) query.getResultList();
	}

}
