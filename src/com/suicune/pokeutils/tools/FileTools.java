package com.suicune.pokeutils.tools;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class FileTools {
	public static final String DEFAULT_SUB_FOLDER = "pokeutils";

	public static final String TAG_POKEMON_1 = "pokemon1";
	public static final String TAG_POKEMON_2 = "pokemon2";
	public static final String TAG_POKEMON_3 = "pokemon3";
	public static final String TAG_POKEMON_4 = "pokemon4";
	public static final String TAG_POKEMON_5 = "pokemon5";
	public static final String TAG_POKEMON_6 = "pokemon6";

	public static void exportTeamToXml(Bundle team) {

	}

	public static Bundle importTeamFromXml(Uri uri) {
		return null;
	}

	public static void exportPokemonToXml(Bundle pokemon) {

	}

	public static Bundle importPokemonFromXml(Uri uri) {
		return null;
	}

	public static File getDefaultFolder() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return null;
		}
		String subFolder = DEFAULT_SUB_FOLDER; // Change to check preferences.
		File mediaStorageDir = new File(
				Environment.getExternalStorageDirectory(), subFolder);
		if (!mediaStorageDir.exists()) {
			if (mediaStorageDir.mkdirs()) {
				Log.d("PokeUtils", "Unable to create xml folder");
			}
		}
		return mediaStorageDir;
	}
}
