package com.vicenteleonardo.CursoUdemySpringBoot.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

	
	public static List<Integer>decodeIntList(String paramUrlSeparadoPorVirgula){
//		String[] vetor = paramUrlSeparadoPorVirgula.split(",");
//		List<Integer> list = new ArrayList<>();
//		
//		for(int i = 0 ; i<vetor.length ; i++) {
//			list.add(Integer.parseInt(vetor[i]));
//		}
//		return list;
		return Arrays.asList(paramUrlSeparadoPorVirgula.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
 	}
	
	public static String decodeParam(String paramUrlComEspacos) {
		try {
			return URLDecoder.decode(paramUrlComEspacos,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
