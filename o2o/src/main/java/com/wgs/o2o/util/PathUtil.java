package com.wgs.o2o.util;

public class PathUtil {
	private static String seperator = System.getProperty("file.separator");

	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/image";

		} else {
			basePath = "/home/wgs/image";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}

	public static String getShopImgPath(long shopId) {
		String imgPath = "/upload/item/shop/" + shopId + "/";
		return imgPath.replace("/", seperator);

	} 

}
