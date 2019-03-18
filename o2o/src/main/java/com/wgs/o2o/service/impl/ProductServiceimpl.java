package com.wgs.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wgs.o2o.dao.ProductDao;
import com.wgs.o2o.dao.ProductImgDao;

import com.wgs.o2o.dto.ImageHolder;
import com.wgs.o2o.dto.ProductExecution;
import com.wgs.o2o.entity.Product;
import com.wgs.o2o.entity.ProductImg;
import com.wgs.o2o.enums.ProductStateEnum;
import com.wgs.o2o.exception.ProductOperationException;
import com.wgs.o2o.service.ProductService;
import com.wgs.o2o.util.ImageUtil;
import com.wgs.o2o.util.PathUtil;

@Service
public class ProductServiceimpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImg;

	@Override
	@Transactional
	// 1.处理缩略图，获取缩略图的相对路径并赋值给product
	// 2.往tb_product写入商品信息，获取productId
	// 3.结合productId批量处理商品详情图
	// 4.将商品详情图列表批量插入tb_product_img
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImg)
			throws ProductOperationException {
		// 空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 给商品设置默认值
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			// 默认为上架状态
			product.setEnableStatus(1);
			// 若商品缩略图不为空时则添加
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				// 创建商品信息
				int effectnum = productDao.insertProduct(product);
				if (effectnum <= 0) {
					throw new ProductOperationException("创建商品失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品失败" + e.getMessage());
			}
			// 若商品详情图不为空则添加
			if (productImg != null && productImg.size() > 0) {
				addProductImg(product, productImg);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);

		} else {
			return new ProductExecution(ProductStateEnum.INNER_ERROR);
		}

	}

	/**
	 * 处理缩略图
	 * 
	 * @param product
	 * @param thumbnail
	 */
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		// TODO Auto-generated method stub
		String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail.getImage(), thumbnail.getName(), dest);
		product.setImgAddr(thumbnailAddr);
	}

	/**
	 * 处理详情图
	 * 
	 * @param product
	 * @param productImg2
	 * @throws ProductOperationException 
	 */
	private void addProductImg(Product product, List<ImageHolder> productImg2) throws ProductOperationException {
		// 获取图片存储路径，这里直接存放到相应店铺文件夹底下
		String dest=PathUtil.getShopImgPath(product.getShop().getShopId());
		List<ProductImg> productImgs=new ArrayList<ProductImg>();
		//遍历图片一次处理并添加进productimg实体类
		for(ImageHolder imageHolder:productImg2) {
			String imgAddr=ImageUtil.generateNormalImg(imageHolder.getImage(),imageHolder.getName(), dest);
		    ProductImg productImg=new ProductImg();
		    productImg.setImgAddr(imgAddr);
		    productImg.setProductId(product.getProductId());
		    product.setCreateTime(new Date());
		    productImgs.add(productImg);
		}
      //如果确实有图片添加的，就执行批量操作
		if(productImgs.size()>0) {
			try {
				int effectnum=productImg.batchinsertProductImg(productImgs);
				if(effectnum<0) {
					throw new ProductOperationException("创建详情图片失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建详情图片失败"+e.getMessage());
			}
		}
	}
}
