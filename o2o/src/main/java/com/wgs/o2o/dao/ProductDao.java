package com.wgs.o2o.dao;

import com.wgs.o2o.entity.Product;

public interface ProductDao {
	/**
	 * 插入商品
	 * @param product
	 * @return
	 */
int insertProduct(Product product);
}
