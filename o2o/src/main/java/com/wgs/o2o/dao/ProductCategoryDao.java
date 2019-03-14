package com.wgs.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wgs.o2o.entity.ProductCategory;
import com.wgs.o2o.entity.Shop;

public interface ProductCategoryDao {
	List<ProductCategory> queryProductCategory(long ShopId);

	/**
	 * 批量添加商品类别
	 * 
	 * @param productCategorylist
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategorylist);
    /**
     * 删除指定的商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId") long shopId);
}
