package com.cmc.y3group.ddd.infrastructure.support;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;


public class Str {
	/** Chars lower. */
	private static final char[] charsLower = new char[] {'0','1','2','3','4','5','6','7','8','9',
		'a','b','c','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

	public static String random(int length){
		return RandomStringUtils.random(length, 0, 0, true, true, null, new SecureRandom());
	}

	public static String randomLower(int length){
		return RandomStringUtils.random(length,  charsLower);
	}


	public static String randomAscii(int length){
		return RandomStringUtils.randomAscii(length);
	}

}
