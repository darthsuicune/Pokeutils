package com.suicune.pokeutils.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.text.TextUtils;

public class DBReader {

	public static ArrayList<HashMap<String, String>> readDB(InputStream stream, ArrayList<String> elements) {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		String line;
		try {
			while ((line = br.readLine()) != null) {
				HashMap<String, String> item = new HashMap<String, String>();
				String[] items = TextUtils.split(line, "  "); 
				for (int i = 0; i < items.length; i++) {
					item.put(elements.get(i), items[i]);
				}
				result.add(item);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
