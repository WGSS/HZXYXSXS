package com.wgs.o2o.dao;




import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wgs.o2o.BaseTest;
import com.wgs.o2o.entity.Area;

public class AreaDaoTest extends BaseTest {
	@Autowired
	private AreaDao areaDao;

	@Test
	public void testQueryArea() {
		List<Area> aList=areaDao.queryArea();
		System.out.println(aList.size());
		
	}
}
