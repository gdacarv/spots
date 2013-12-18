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
}
