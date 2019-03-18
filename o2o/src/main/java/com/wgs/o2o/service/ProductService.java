package com.wgs.o2o.service;

import java.util.List;

import com.wgs.o2o.dto.ImageHolder;
import com.wgs.o2o.dto.ProductExecution;
import com.wgs.o2o.entity.Product;
import com.wgs.o2o.exception.ProductOperationException;

public interface ProductService {
	/**
	 * 添加商品以及图片处理
	 * 
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImg) throws ProductOperationException;

}
