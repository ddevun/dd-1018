package org.example.rental_agreement.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Service to calculate the chargeable days.
 */
public class ChargingService {

    /**
     * Adds days to the given date.
     * @param date The date to add to.
     * @param days The number of days to add.
     * @return The new date.
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    /**
     * Gets the end date given a length.
     * @param startDate The date to start from.
     * @param length the number of days.
     * @return The end date in a Date object.
     */
    public static Date getEndDate(Date startDate, int length) {
        return getEndDateCal(startDate, length).getTime();
    }

    /**
     * Gets the end date given a length.
     * @param startDate The date to start from.
     * @param length The number of days.
     * @return The end date in a Calendar object.
     */
    public static Calendar getEndDateCal(Date startDate, int length) {
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(startDate);
        calEnd.add(Calendar.DAY_OF_YEAR, length);
        return calEnd;
    }


    /** Converts a Date object to a Calendar object.
     *
     * @param date The given date.
     * @return The date in a Calendar object.
     */
   public static Calendar dateToCal(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * Gets the number of chargeable days.
     * @param startDate The date to start from.
     * @param length The number of days.
     * @param weekdayCharge true if there is a charge for weekdays.
     * @param weekendCharge true if there is a charge for weekends.
     * @param holidayCharge true if there is a charge for holidays.
     * @return the number of chargeable days.
     */
    public static long getChargeableDays(Date startDate, int length, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        Calendar calStart = dateToCal(startDate);
        Calendar calEnd = getEndDateCal(startDate, length);

        long totalDays = Duration.between(calStart.toInstant(), calEnd.toInstant()).toDays();
        long weekendDays = getWeekendDays(calStart, calEnd);
        long weekDays = totalDays - weekendDays;

        // check if weekdays and weekends are chargeable
        long chargeableDays = totalDays;
        if (!weekendCharge) {
            chargeableDays-= weekendDays;
        }
        if (!weekdayCharge) {
            chargeableDays-= weekDays;
        }
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
                    // Avoid subtracting the day twice if the holiday falls on a day that has already been subtracted
                    boolean holidayAlreadySubtracted = false;
                    if (isWeekend(holiday)) {
                        if (!weekendCharge) {
                            holidayAlreadySubtracted = true;
                        }
                    } else if (!weekdayCharge) {
                            holidayAlreadySubtracted = true;

                    }
                    if (!holidayAlreadySubtracted) {
                        chargeableDays--;
                    }
                }
            }


        }
        return chargeableDays;

    }

    /**
     * Returns if the date falls on a weekend.
     * @param date the given date.
     * @return true if the date falls on a weekend.
     */
    private static boolean isWeekend(Date date) {
        int dow = dateToCal(date).get(Calendar.DAY_OF_WEEK);
        return (dow == 1 || dow == 7);
    }

    /**
     * Gets the number of weekend days in a period.
     * @param calStart the start of the period.
     * @param calEnd the end of the period.
     * @return The number of weekend days.
     */
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


    /**
     * Creates a list of holiday days. Currently only checks Labor day and Independence day.
     * @param year The year of the holiday.
     * @return a list of holiday dates.
     */
    public static List<Date> getHolidays(int year) {
        List<Date> holidays = new ArrayList<>();
        holidays.add(getLaborDay(year));
        holidays.add(getIndependenceDayDate(year));

        return holidays;
    }

    /**
     * Gets the date of Independence day in a given year.
     * @param year The year of the holiday.
     * @return The date of Independence day.
     */
    private static Date getIndependenceDayDate(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        // Calendar.MONTH is 0 indexed
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.DAY_OF_MONTH, 4);

        // adjust to the nearest weekday if the holiday falls on a weekend.
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        return cal.getTime();

    }

    /**
     * Returns the date of Labor Day in a given year.
     * @param year The year to check.
     * @return the date of Labor Day.
     */
    private static Date getLaborDay(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        int dow = cal.get(Calendar.DAY_OF_WEEK);
        int offset = (8 - dow + 1) % 7;
        cal.add(Calendar.DAY_OF_MONTH, offset);
        return cal.getTime();
    }

}
