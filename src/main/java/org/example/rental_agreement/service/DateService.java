package org.example.rental_agreement.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Gets the relevent holiday dates for this year.
 */
public class DateService {

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }
    public static Date getFinalDate(Date startDate, int length) {
        return getFinalDateCal(startDate, length).getTime();
    }
    public static Calendar getFinalDateCal(Date startDate, int length) {
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(startDate);
        // offset allows for us to account for cases where we don't want to include the day after the last day of the rental agreement
        calEnd.add(Calendar.DAY_OF_YEAR, length);
        return calEnd;
    }

   public static Calendar dateToCal(Date startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        return cal;
    }
    public static long getChargeableDays(Date startDate, int length, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        Calendar calStart = dateToCal(startDate);
        // offset is set to zero here, since Duration.between is not inclusive of the second date.
        Calendar calEnd = getFinalDateCal(startDate, length);

        long chargeableDays = Duration.between(calStart.toInstant(), calEnd.toInstant()).toDays();

        // Check for non-chargeable holidays
        if (!holidayCharge) {
            List<Date> holidays = getHolidays(calStart.get(Calendar.YEAR));
            // Edge case to handle start dates near the end of the year
            if (calEnd.get(Calendar.YEAR) != calStart.get(Calendar.YEAR)) {
                holidays.addAll(getHolidays(calEnd.get(Calendar.YEAR)));
            }
            // Loop through holidays
            for (Date holiday : holidays) {
                if ((holiday.after(calStart.getTime())
                        && holiday.before(calEnd.getTime()))
                        || holiday.equals(calStart.getTime())) {
                    chargeableDays--;
                }
            }
        }
        if (!weekendCharge) {
            chargeableDays-= getWeekendDays(calStart, calEnd);
        }
        return chargeableDays;

    }

    private static int getWeekendDays(Calendar calStart, Calendar calEnd) {
        long totalDays = Duration.between(calStart.toInstant(), calEnd.toInstant()).toDays();
        int fullWeeks = (int)(totalDays / 7);
        int weekendDays = fullWeeks * 2;

        int remainingDays = (int)(totalDays % 7);
        // subtract one to zero index
        int dowStart = calStart.get(Calendar.DAY_OF_WEEK) - 1;
        for (int i = 0; i < remainingDays; i++) {
            int day = (dowStart + i) % 7;
            if (day == 0 || day == 6) {
                weekendDays++;
            }
        }
        return weekendDays;
    }

    public static List<Date> getHolidays(int year) {
        List<Date> holidays = new ArrayList<>();
        holidays.add(getLaborday(year));
        holidays.add(getIndependenceDayDate(year));

        return holidays;
    }

    private static Date getIndependenceDayDate(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        // Calendar.MONTH is 0 indexed
        cal.set(Calendar.MONTH, 6);
        cal.set(Calendar.DAY_OF_MONTH, 4);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        return cal.getTime();

    }
    private static Date getLaborday(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        // Calendar.MONTH is 0 indexed
        cal.set(Calendar.MONTH, 8);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int dow = cal.get(Calendar.DAY_OF_WEEK);
        int offset = (8 - dow + 1) % 7;
        cal.add(Calendar.DAY_OF_MONTH, offset);
        return cal.getTime();
    }

}
