package com.cmc.y3group.ddd.infrastructure.support;

import java.time.Instant;

public final class GenerateID {
	/**
	 * Ex: 1218314313351896914
	 */
	public static String generate() {
		long prefix = (long) (Math.floor(Math.random() * 26) + 97);
		long postfix = Long.parseLong(String.valueOf(Math.random()).substring(2)) + Instant.now().toEpochMilli();
		return prefix + String.valueOf(postfix);
	}
}
