package com.example.week4day3hw.model.FlickerObj;

import com.google.gson.annotations.SerializedName;

public class Media{

	@SerializedName("m")
	private String M;

	public void setM(String M){
		this.M = M;
	}

	public String getM(){
		return M;
	}

	@Override
 	public String toString(){
		return 
			"Media{" + 
			"m = '" + M + '\'' + 
			"}";
		}
}