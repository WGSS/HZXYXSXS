package com.wgs.o2o.dto;

import java.util.List;

import com.wgs.o2o.entity.Product;
import com.wgs.o2o.enums.ProductStateEnum;

public class ProductExecution {
	// 结果状态
	private int state;
	// 状态标识
	private String stateInfo;
	// 商品数量
	private int count;
	// 操作的Product（增删改的时候用）
	private Product product;
	// 获取product列表（查询商品列表的时候用）
	private List<Product> pList;

	public ProductExecution() {
	}

	// 失败时的构造器
	public ProductExecution(ProductStateEnum pStateEnum) {
		this.state = pStateEnum.getState();
		this.stateInfo = pStateEnum.getStateInfo();
	}

	// 成功时的构造器
	public ProductExecution(ProductStateEnum pStateEnum, Product product) {
		this.state = pStateEnum.getState();
		this.stateInfo = pStateEnum.getStateInfo();
		this.product = product;

	}

	// 成功时的构造器
	public ProductExecution(ProductStateEnum pStateEnum, List<Product> pList) {
		this.state = pStateEnum.getState();
		this.stateInfo = pStateEnum.getStateInfo();
		this.pList = pList;

	}
}
