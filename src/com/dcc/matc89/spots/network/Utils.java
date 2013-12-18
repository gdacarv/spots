package com.dcc.matc89.spots.network;

import java.io.InputStream;

public class Utils {
	static String convertInputStreamToString(InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
		try{
			return s.hasNext() ? s.next() : "";
		} finally {
			s.close();
		}
	}

	public static String getUrl(String url, String[] params) {
		if(params == null || params.length == 0)
			return url;
		StringBuilder builder = new StringBuilder(url).append('?');
		for(int i = 0; i < params.length-1; i+=2){
			if(i > 0)
				builder.append('&');
			builder.append(params[i]).append('=').append(params[i+1]);
		}
		return builder.toString();
	}
}
