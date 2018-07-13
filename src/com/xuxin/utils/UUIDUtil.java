package com.xuxin.utils;

import java.util.UUID;

public class UUIDUtil {
	public static String getUUID(){
		return UUID.randomUUID().toString();//得到一个随机的字符串
	}
}
