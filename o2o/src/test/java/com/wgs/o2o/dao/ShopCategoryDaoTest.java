package com.wgs.o2o.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wgs.o2o.BaseTest;
import com.wgs.o2o.entity.Area;
import com.wgs.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest  {
	@Autowired
	private ShopCategoryDao shopCategoryDao;

	@Test
	public void testQueryShopCategory() {
		 
		ShopCategory test=new ShopCategory();
		ShopCategory ParentCategory=new ShopCategory();
		ParentCategory.setShopCategoryId(1L);
		test.setParent(ParentCategory);
		List<ShopCategory> sList=shopCategoryDao.queryShopCategory(test);
		System.out.println(sList.get(0).getShopCategoryName());
}}
