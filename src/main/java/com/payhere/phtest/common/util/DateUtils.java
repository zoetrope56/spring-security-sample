package com.payhere.phtest.common.util;

import com.payhere.phtest.common.constants.CommonConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

	public static LocalDate toLocalDate(String date) {
		return LocalDate.parse(date, DateTimeFormatter.ofPattern(CommonConstants.DEFAULT_DATE_FORMAT));
	}

	public static LocalDateTime toLocalDateTime(String date) {
		return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(CommonConstants.DEFAULT_DATETIME_FORMAT));
	}

	public static String dateToString(LocalDate localDate) {
		return localDate.format(DateTimeFormatter.ofPattern(CommonConstants.DEFAULT_DATE_FORMAT));
	}

	public static String datetomeToString(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern(CommonConstants.DEFAULT_DATETIME_FORMAT));
	}

	public static String todayLocalDateToString() {
		return LocalDate.now(ZoneId.of("Asia/Seoul")).toString();
	}

	public static LocalDate todayLocalDate() {
		return LocalDate.now(ZoneId.of("Asia/Seoul"));
	}

	public static LocalDateTime todayLocalDateTime() {
		return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
	}

	public static LocalDateTime convertUtcToKst(LocalDateTime utcDateTime) {
		return utcDateTime.plusHours(9);
	}

	public static LocalDate yesterdayLocalDate() {
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
		return today.minusDays(1);
	}

	public static String parseMonthAndDay(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd");
		return date.format(formatter);
	}

	public static String parseYearAndMonthAndDay(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		return date.format(formatter);
	}

	public static String nowDayOfWeek(LocalDate now) {
		return now.getDayOfWeek().toString();
	}

	public static boolean isSameDate(LocalDateTime localDateTime, LocalDate localDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return localDateTime.format(formatter).equals(localDate.format(formatter));
	}

}
