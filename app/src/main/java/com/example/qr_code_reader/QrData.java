package com.example.qr_code_reader;

import com.google.gson.annotations.SerializedName;

public class QrData{

	@SerializedName("image")
	private String image;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"QrData{" + 
			"image = '" + image + '\'' + 
			",phone = '" + phone + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}