/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.component.essential.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

public class LocalDateTimeUtils {

	private static boolean chronoUnitBefore(ChronoUnit chronoUnit, ChronoUnit chronoUnit1) {
		return chronoUnit.getDuration().getSeconds() >= chronoUnit1.getDuration().getSeconds();
	}

	public static List<LocalDate> getMiddleDate(LocalDate begin, LocalDate end) {
		ChronoUnit.DAYS.between(begin, end);
		List<LocalDate> localDateList = new ArrayList<>(10);
		long length = end.toEpochDay() - begin.toEpochDay();
		for (long i = length; i >= 0; i--) {
			localDateList.add(end.minusDays(i));
		}
		return localDateList;
	}

	public static LocalDateTime createZeroLocalDateTime() {
		return LocalDateTime.of(LocalDate.now(), createZeroLocalTime());
	}

	public static LocalDateTime createLastLocalDateTime() {
		return LocalDateTime.of(LocalDate.now(), createLastLocalTime());
	}

	public static LocalTime createZeroLocalTime() {
		return LocalTime.of(0, 0);
	}

	public static LocalTime createLastLocalTime() {
		return LocalTime.of(23, 59, 59);
	}

	public static LocalDateTime truncatedTo(LocalDateTime localDateTime, ChronoUnit chronoUnit) {
		int year = localDateTime.getYear();
		int month = chronoUnitBefore(ChronoUnit.MONTHS, chronoUnit) ? localDateTime.getMonthValue() : 0;
		int dayOfMonth = chronoUnitBefore(ChronoUnit.DAYS, chronoUnit) ? localDateTime.getDayOfMonth() : 0;
		int hour = chronoUnitBefore(ChronoUnit.HOURS, chronoUnit) ? localDateTime.getHour() : 0;
		int minute = chronoUnitBefore(ChronoUnit.MINUTES, chronoUnit) ? localDateTime.getMinute() : 0;
		int second = chronoUnitBefore(ChronoUnit.SECONDS, chronoUnit) ? localDateTime.getSecond() : 0;
		return LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
	}

	public static LocalDate truncatedTo(LocalDate localDateTime, ChronoUnit chronoUnit) {
		int year = localDateTime.getYear();
		int month = chronoUnitBefore(ChronoUnit.MONTHS, chronoUnit) ? localDateTime.getMonthValue() : 0;
		int dayOfMonth = chronoUnitBefore(ChronoUnit.DAYS, chronoUnit) ? localDateTime.getDayOfMonth() : 0;
		return LocalDate.of(year, month, dayOfMonth);
	}

	
	public static long between(ChronoUnit chronoUnit, Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
		return chronoUnit.between(temporal1Inclusive, temporal2Exclusive);
	}

	public static boolean isAfter(ChronoLocalDate compare1, ChronoLocalDate compare2) {
		return compare1.isAfter(compare2);
	}

	public static boolean isBefore(ChronoLocalDate compare1, ChronoLocalDate compare2) {
		return compare1.isBefore(compare2);
	}

	public boolean isEqual(ChronoLocalDate compare1, ChronoLocalDate compare2) {
		return compare1.isEqual(compare2);
	}

}
