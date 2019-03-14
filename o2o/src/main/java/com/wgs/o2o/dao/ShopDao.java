package com.wgs.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wgs.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 分页查询店铺 ，可以输入的条件：店铺名（模糊），店铺状态，店铺类别，区域id,onwer
	 * 
	 * @param shopCodition
	 * 			@Param("shopCondition")这个注解是给xml那里识别的
	 * @param rowIndex
	 *            从第几行开始取
	 * @param pageSize
	 *            返回的条数
	 * @return
	 */

	List<Shop> queryShopList(@Param("shopCondition") Shop shopCodition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 返回queryShopList总数
	 * 
	 * @param shopCodition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCodition);

	/**
	 * 查询店铺byId
	 * 
	 * @param ShopId
	 * @return
	 */
	Shop queryByShopId(long ShopId);

	/**
	 * 新增店铺
	 * 
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);

	/**
	 * 更新店铺
	 * 
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);

}
