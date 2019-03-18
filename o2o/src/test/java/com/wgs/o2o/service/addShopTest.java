package com.wgs.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wgs.o2o.BaseTest;
import com.wgs.o2o.dto.ImageHolder;
import com.wgs.o2o.dto.ShopExecution;
import com.wgs.o2o.entity.Area;
import com.wgs.o2o.entity.PersonInfo;
import com.wgs.o2o.entity.Shop;
import com.wgs.o2o.entity.ShopCategory;
import com.wgs.o2o.enums.ShopstateEnum;
import com.wgs.o2o.exception.ShopOperationException;

public class addShopTest extends BaseTest {
   @Autowired
   private ShopService shopService;
   @Test
   public void getShopList() {
	   Shop shopCondition=new Shop();
	   ShopCategory shopCategory=new ShopCategory();
	   shopCategory.setShopCategoryId(2L);
	   shopCondition.setShopCategory(shopCategory);
	   ShopExecution se=shopService.getShopList(shopCondition, 1, 2);
	   System.out.println("选取的店铺数"+se.getShopList().size());
	   System.out.println("店铺总数"+se.getCount());
	   
	   
   }
   
   @Test
   @Ignore
   public void testmodifyShop()throws ShopOperationException,FileNotFoundException {
	   Shop shop=new Shop();
	   shop.setShopId(1L);
	   shop.setShopName("更新后的名字");
	   File shopImg=new File("D:/5778.jpg");
		  InputStream iStream=new FileInputStream(shopImg);
		  ImageHolder imageHolder=new ImageHolder(shopImg.getName(), iStream);
		  ShopExecution shopExecution=shopService.modifyShop(shop,imageHolder);
        System.out.println(shopExecution.getShop().getShopImg());
   }
   
   
   @Test
   @Ignore
   public void addTest() throws FileNotFoundException {
	   Shop shop=new Shop();
		  PersonInfo owner=new PersonInfo();
		  Area area=new Area();
		  ShopCategory shopCategory=new ShopCategory();
		  owner.setUserId(1L);
		  area.setAreaId(2);
		  shopCategory.setShopCategoryId(2L);
		  shop.setOwner(owner);
		  shop.setArea(area);
		  shop.setShopCategory(shopCategory);
		  shop.setShopName("测试商铺33");
		  shop.setShopDesc("测试商铺描");
		  shop.setShopAddr("地址");
		  shop.setPriority(1);
		  shop.setPhone("test");

		  shop.setCreateTime(new Date());
		  shop.setEnableStatus(1);
		  shop.setAdvice("审核中");
		  File shopImg=new File("D:/7625.jpg");
		  InputStream iStream=new FileInputStream(shopImg);
		  ImageHolder imageHolder=new ImageHolder(shopImg.getName(), iStream);
		  ShopExecution shopExecution=shopService.addshop(shop,imageHolder);
		  assertEquals(ShopstateEnum.CHECK.getState(),shopExecution.getState());
	   
   }
}
