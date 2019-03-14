package com.wgs.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.wgs.o2o.BaseTest;
import com.wgs.o2o.entity.Product;
import com.wgs.o2o.entity.ProductCategory;
import com.wgs.o2o.entity.Shop;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {
	@Autowired
	private ProductDao pDao;

	@Test
	public void testAinsertProduct() {
		Product product = new Product();
		product.setCreateTime(new Date());
		product.setEnableStatus(1);
		product.setImgAddr("q12324123wqds");
		product.setLastEditTime(new Date());
		product.setNormalPrice("234");
		product.setPriority(30);
		product.setProductDesc("不顶做的奶茶");
		product.setProductName("奶茶");
		product.setPromotionPrice("456");
		Shop shop = new Shop();
		shop.setShopId(1L);
		product.setShop(shop);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(1L);
		product.setProductCategory(productCategory);
		int num=pDao.insertProduct(product);
		assertEquals(1, num);
	}

}
