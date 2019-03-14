package com.wgs.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.Versioned;
import com.wgs.o2o.BaseTest;
import com.wgs.o2o.entity.Area;
import com.wgs.o2o.entity.PersonInfo;
import com.wgs.o2o.entity.Shop;
import com.wgs.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest  {
  @Autowired
  private ShopDao shopDao;

  /* @Test
  public void insertShop() {
	  Shop shop=new Shop();
	  PersonInfo owner=new PersonInfo();
	  Area area=new Area();
	  ShopCategory shopCategory=new ShopCategory();
	  owner.setUserId(1L);
	  area.setAreaId(2);
	  shopCategory.setShopCategotyId(1L);
	  shop.setOwner(owner);
	  shop.setArea(area);
	  shop.setShopCategory(shopCategory);
	  shop.setShopName("测试商铺");
	  shop.setShopDesc("测试商铺描述");
	  shop.setShopAddr("地址");
	  shop.setPriority(1);
	  shop.setPhone("test");
	  shop.setShopImg("test");
	  shop.setCreateTime(new Date());
	  shop.setEnableStatus(1);
	  shop.setAdvice("审核中");
	  int effect=shopDao.insertShop(shop);
	  assertEquals(1, effect);*/
	/*
	public void qureyShop() {
		long shopId=1L;
		Shop shop=shopDao.queryByShopId(shopId);
		System.out.println("areaId"+shop.getArea().getAreaId());
		System.out.println("areaName"+shop.getArea().getAreaName());
	}*/
	/*public void updateShop() {
		  Shop shop=new Shop();
		  shop.setShopId(1L);
		  PersonInfo owner=new PersonInfo();
		  Area area=new Area();
		  ShopCategory shopCategory=new ShopCategory();
		  area.setAreaId(1);
		 
		
		  shop.setArea(area);
		  
		  shop.setShopName("商铺");
		  shop.setShopDesc("商铺描述");
		  shop.setShopAddr("地址");
		  shop.setPriority(1);
		  shop.setPhone("test");
		  shop.setShopImg("test");
		  shop.setLastEditTime(new Date());
		  shop.setEnableStatus(1);
		  shop.setAdvice("审核中");
		  int effect=shopDao.updateShop(shop);
		  assertEquals(1, effect);
}*/
  @Test
  public void queryShopList() {
	  Shop shopCondition=new Shop();
	  PersonInfo owner=new PersonInfo();
	  owner.setUserId(1L);
	  shopCondition.setOwner(owner);
	  List<Shop> shop=shopDao.queryShopList(shopCondition, 0, 5);
	  System.out.println("选取的店铺数量"+shop.size());
	  int count=shopDao.queryShopCount(shopCondition);
	  System.out.println("指定拥有者的店铺总数"+count);
  }
	
}
