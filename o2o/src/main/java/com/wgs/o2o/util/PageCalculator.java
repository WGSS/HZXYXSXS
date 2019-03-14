package com.wgs.o2o.util;

public class PageCalculator {
	public static int calculateRowIndex(int pageIndex, int pageSize) {
		return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
// 数据库是从0开始的，如果pageIndex（页码）是1 pagesize是5，则返回的行数是第0行开始，页码是2返回的行数是5
// 数据库只认行数，前端认页码
	}
}
