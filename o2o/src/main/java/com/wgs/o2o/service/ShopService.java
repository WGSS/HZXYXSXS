package com.wgs.o2o.service;

import java.io.File;
import java.io.InputStream;

import com.wgs.o2o.dto.ShopExecution;
import com.wgs.o2o.entity.Shop;
import com.wgs.o2o.exception.ShopOperationException;

public interface ShopService {
	/**
	 * 
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	
	/**
	 * 通过id查询店铺
	 * 
	 * @param shopId
	 * @return
	 */
	Shop getShopById(Long shopId);

	/**
	 * 更新图片 包括对图片的处理
	 * 
	 * @param shop
	 * @param ShopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution modifyShop(Shop shop, InputStream ShopImgInputStream, String fileName) throws ShopOperationException;

	/**
	 * 注册店铺 包括图片的处理
	 * 
	 * @param shop
	 * @param ShopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution addshop(Shop shop, InputStream ShopImgInputStream, String fileName) throws ShopOperationException;

}
