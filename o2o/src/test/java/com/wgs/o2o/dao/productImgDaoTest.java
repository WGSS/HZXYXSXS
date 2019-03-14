package com.wgs.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.wgs.o2o.BaseTest;
import com.wgs.o2o.entity.Product;
import com.wgs.o2o.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class productImgDaoTest extends BaseTest {
	@Autowired
	private ProductImgDao pDao;
	@Test
	public void testAbatchinsertimg() {
		ProductImg pImg1=new ProductImg();
		pImg1.setCreateTime(new Date());
		pImg1.setImgAddr("ssfase");
		pImg1.setImgDesc("aini");
		pImg1.setPriority(1);
		
		pImg1.setProductId(7L);
		ProductImg pImg2=new ProductImg();
		pImg2.setCreateTime(new Date());
		pImg2.setImgAddr("ssfadsfse");
		pImg2.setImgDesc("ainiwdsd");
		pImg2.setPriority(1231);
		
		pImg2.setProductId(7L);
		List<ProductImg> list=new ArrayList<ProductImg>();
		list.add(pImg1);
		list.add(pImg2);
		int num=pDao.batchinsertProductImg(list);
		assertEquals(2, num);
	}

}
