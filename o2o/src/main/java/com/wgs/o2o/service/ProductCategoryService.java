package com.wgs.o2o.service;

import java.util.List;

import com.wgs.o2o.dto.ProductCategoryExecution;
import com.wgs.o2o.entity.ProductCategory;
import com.wgs.o2o.exception.ProductCategoryOperationException;

public interface ProductCategoryService {
	/**
	 * 查询指定店铺的商品类别
	 * @param shopId
	 * @return
	 */
    List<ProductCategory> getProductCategoryList(long shopId);
  
    
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategorylist)throws ProductCategoryOperationException;

    ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId)throws ProductCategoryOperationException; 
}
