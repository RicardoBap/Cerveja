package com.ricbap.brewer.security;

import java.time.LocalDate;
import java.time.Month;

public class GeradorDataMileSegundos {
	
	public static void main(String[] args) {
		
		LocalDate date = LocalDate.of(2021, Month.JANUARY, 1);
		System.out.println(date.toEpochDay() * 24 * 60 * 60);
	}

}
