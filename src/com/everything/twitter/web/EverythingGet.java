package com.everything.twitter.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class EverythingGet {

	public static void addParams(StringBuilder sb, String paramName,
			String paramValue) {
		if (sb.length() > 0) {
			sb.append("&");
		}
		sb.append(paramName);
		sb.append("=");
		sb.append(paramValue);
	}
	
	public static String doGet(String json, String urlAddr) {
		URL url;
		StringBuilder builder = new StringBuilder();
		String retVal;
		try {
			url = new URL(urlAddr);

			URLConnection conn = url.openConnection();
			HttpURLConnection connection = (HttpURLConnection) conn;
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.connect();
			OutputStreamWriter wr = new OutputStreamWriter(
					connection.getOutputStream());

			wr.write(json);

			wr.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			String line;

			while ((line = rd.readLine()) != null) {
				builder.append(line);
			}

			wr.close();
			rd.close();
			retVal = builder.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			retVal = null;
		} catch (IOException e) {
			e.printStackTrace();
			retVal = null;
		}

		return retVal;
	}
}
