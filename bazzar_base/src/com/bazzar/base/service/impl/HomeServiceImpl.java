package com.bazzar.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazzar.base.dao.HomeDao;
import com.bazzar.base.domain.Home;
import com.bazzar.base.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	HomeDao homeDao;
	
	public Long create(Home home) {
		return homeDao.create(home);
	}
	public void edit(Home home) {
		homeDao.edit(home);
	}
	public Home get(Long id) {
		return homeDao.get(id);
	}

}
