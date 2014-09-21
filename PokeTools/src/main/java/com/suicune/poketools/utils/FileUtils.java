package com.suicune.poketools.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by denis on 20.09.14.
 */
public class FileUtils {
	public static JSONObject toJson(InputStream stream) throws JSONException, IOException {
		return new JSONObject(toString(stream));
	}

	public static JSONArray toJsonArray(InputStream stream) throws JSONException, IOException {
		return new JSONArray(toString(stream));
	}

	public static String toString(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder builder = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			builder.append(line);
			line = reader.readLine();
		}
		reader.close();
		return builder.toString();
	}
}
