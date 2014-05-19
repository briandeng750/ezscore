package org.lccgymnastics.ezscore.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Globals {
	public static Gson GSON;
	static {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("MMM dd, yyyy");
		GSON = builder.create();
	}
}
