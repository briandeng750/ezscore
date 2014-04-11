package org.lccgymnastics.ezscore.model;

import com.google.gson.Gson;

public class Globals {
	public static Gson GSON;
	static {
		GSON = new Gson();
	}
}
