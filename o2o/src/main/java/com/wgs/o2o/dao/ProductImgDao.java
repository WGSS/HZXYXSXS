package com.wgs.o2o.dao;

import java.util.List;

import com.wgs.o2o.entity.ProductImg;

public interface ProductImgDao {
	/**
	 * 批量增加图片
	 * @param pImgs
	 * @return
	 */
 int batchinsertProductImg(List<ProductImg> pImgs);
}
