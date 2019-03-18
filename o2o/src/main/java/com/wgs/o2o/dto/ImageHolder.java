package com.wgs.o2o.dto;

import java.io.InputStream;

public class ImageHolder {
	private String name;
	private InputStream image;

	public ImageHolder(String name, InputStream image) {
		this.name = name;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

}
