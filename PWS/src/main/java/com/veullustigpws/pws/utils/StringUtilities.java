package com.veullustigpws.pws.utils;

public class StringUtilities {
	public static int getWordCount(String str) {
		return str.split("\\s+").length;
	}
}
