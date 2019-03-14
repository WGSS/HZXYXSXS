package com.wgs.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.wgs.o2o.BaseTest;
import com.wgs.o2o.entity.ProductCategory;
import com.wgs.o2o.entity.Shop;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryTest extends BaseTest {
   @Autowired
   private ProductCategoryDao pDao;
   
   @Test
   public void testBgetProductCategoryList() {	
	   Long shopId=1L;
	   List<ProductCategory> pList=pDao.queryProductCategory(shopId);
	   System.out.println(pList.size());
   }
   @Test 
   public void testABatchInsertProductCategory()throws Exception {
	   ProductCategory p1=new ProductCategory();
	  
	   p1.setProductCategoryName("炒饭");
	   p1.setPriority(2);
	   p1.setCreateTime(new Date());
	   p1.setShopId(1L);
	   ProductCategory p2=new ProductCategory();
	
	   p2.setProductCategoryName("火锅");
	   p2.setPriority(3);
	   p2.setCreateTime(new Date());
	   p2.setShopId(1L);
	   List<ProductCategory> list=new ArrayList<ProductCategory>();
	   list.add(p1);
	   list.add(p2);
	   int num=pDao.batchInsertProductCategory(list);
	   assertEquals(2, num);
   }
   @Test
   public void testCdeleteProductCategoryById() {
	   Long shopId=1L;
	   List<ProductCategory> list=pDao.queryProductCategory(shopId);
	   for(ProductCategory productCategory:list) {
		   if("炒饭".equals(productCategory.getProductCategoryName())||"火锅".equals(productCategory.getProductCategoryName())) {
			   int num=pDao.deleteProductCategory(productCategory.getProductCategoryId(),shopId);
			   assertEquals(1, num);
		   }
	   }
   }
}
