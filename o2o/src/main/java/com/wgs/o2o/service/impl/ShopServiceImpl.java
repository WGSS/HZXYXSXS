package com.wgs.o2o.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wgs.o2o.dao.ShopDao;
import com.wgs.o2o.dto.ImageHolder;
import com.wgs.o2o.dto.ShopExecution;
import com.wgs.o2o.entity.Shop;
import com.wgs.o2o.enums.ShopstateEnum;
import com.wgs.o2o.exception.ShopOperationException;
import com.wgs.o2o.service.ShopService;
import com.wgs.o2o.util.ImageUtil;
import com.wgs.o2o.util.PageCalculator;
import com.wgs.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;

	@Transactional
	public ShopExecution addshop(Shop shop,ImageHolder thumbnail) {
		// 空值判断
		if (shop == null) {
			return new ShopExecution(ShopstateEnum.NULL);
		}

		try {
			// 给店铺信息赋初始值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			// 添加店铺信息
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				throw new ShopOperationException("店铺创建失败");
			} else {
				if (thumbnail.getImage() != null) {
					// 储存图片
					try {
						addShopImg(shop, thumbnail);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error" + e.getMessage());
					}
					// 更新店铺的图片地址
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}

		} catch (Exception e) {
			throw new ShopOperationException("addShop error" + e.getMessage());
		}
		return new ShopExecution(ShopstateEnum.CHECK, shop);
	}

	private void addShopImg(Shop shop, ImageHolder thumbnail) {
		// 获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImgPath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(thumbnail.getImage(), thumbnail.getName(), dest);
		shop.setShopImg(shopImgAddr);

	}

	@Override
	public Shop getShopById(Long shopId) {
		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail)
			throws ShopOperationException {
		//判断商店是否为空
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopstateEnum.NULL);
		} else {
			try {
				// 1.判断是否需要处理图片
				if (thumbnail.getImage() != null && thumbnail.getName() != null && !"".equals(thumbnail.getName())) {
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					if (tempShop.getShopImg() != null) {
						ImageUtil.deleteFileOrPath(tempShop.getShopImg());
					}
					addShopImg(shop, thumbnail);
				}
				// 2.更新店铺信息
				shop.setLastEditTime(new Date());
				int effectnum = shopDao.updateShop(shop);
				if (effectnum <= 0) {
					return new ShopExecution(ShopstateEnum.INNER_ERROR);
				} else {
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopstateEnum.SUCCESS, shop);
				}
			} catch (Exception e) {
				throw new ShopOperationException("modify err" + e.getMessage());
			}

		}

	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex=PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList=shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count=shopDao.queryShopCount(shopCondition);
		ShopExecution se=new ShopExecution();
		if(shopList!=null) {
			se.setShopList(shopList);
			se.setCount(count);
		}else {
			se.setState(ShopstateEnum.INNER_ERROR.getState());
		}
		return se;
	}

}
