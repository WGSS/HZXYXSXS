package com.wgs.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wgs.o2o.dao.ProductCategoryDao;
import com.wgs.o2o.dto.ProductCategoryExecution;
import com.wgs.o2o.entity.ProductCategory;
import com.wgs.o2o.enums.ProductCategoryStateEnum;
import com.wgs.o2o.exception.ProductCategoryOperationException;
import com.wgs.o2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceimpl implements ProductCategoryService {
	@Autowired
	private ProductCategoryDao pDao;

	@Override
	public List<ProductCategory> getProductCategoryList(long shopId) {
		// TODO Auto-generated method stub
		return pDao.queryProductCategory(shopId);
	}

	@Override
	@Transactional
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategorylist)
			throws ProductCategoryOperationException {
		if (productCategorylist != null && productCategorylist.size() > 0) {
			try {
				int effectNum = pDao.batchInsertProductCategory(productCategorylist);
				if (effectNum <= 0) {
					throw new ProductCategoryOperationException("商品类别创建失败");

				} else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);

				}

			} catch (Exception e) {
				throw new ProductCategoryOperationException("batchAddProductCategory error" + e.getMessage());
			}
		} else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.INNER_ERROR);
		}

	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		// TODO 将此商品类别下的商品的类别id置为null
		try {
			int num=pDao.deleteProductCategory(productCategoryId, shopId);
			if(num<=0) {
				throw new ProductCategoryOperationException("商品类别删除失败");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			throw new ProductCategoryOperationException("deleteproductCategory err!"+e.getMessage());
		}
		
	}

}
