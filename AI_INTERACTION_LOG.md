Q: `algorithm to determine labor day date`

A:
```
Here’s a simple algorithm to determine the date of Labor Day (U.S.) for any given year:
Definition:
Labor Day in the United States is observed on the first Monday in September.

🧮 Algorithm (Step-by-Step)

Input: year
Output: Date of Labor Day for that year

Set month = 9 (September).

Set day = 1 (start from September 1).

Find the day of the week for September 1 of that year.

Let’s call it dow (0 = Sunday, 1 = Monday, …, 6 = Saturday).

Compute offset:

offset = (8 - dow) % 7


This calculates how many days to add to reach the next Monday (if Sept 1 is Monday, offset = 0).

Labor Day = September 1 + offset days.

💻 Example in Pseudocode
function getLaborDay(year):
date = September 1 of given year
dow = dayOfWeek(date)  # Sunday=0, Monday=1, ..., Saturday=6
offset = (8 - dow) % 7
laborDay = date + offset days
return laborDay

🧠 Example

For 2025:

Sept 1, 2025 → Monday (dow = 1)

offset = (8 - 1) % 7 = 0

Labor Day = September 1, 2025

✅ Result: September 1, 2025
```

Q: `algorithm to get number of weekend days in a date range`

A:
```
Here’s a clear and efficient algorithm to determine the number of weekend days (Saturdays and Sundays) between two dates (inclusive).

🧮 Algorithm: Count Weekend Days in a Date Range

Input:

startDate

endDate (inclusive or exclusive—see note below)

Output:

Number of weekend days in the range.

Step-by-Step Logic

Ensure order
If startDate > endDate, swap them.

Compute total days

totalDays = (endDate - startDate).days + 1


Compute number of full weeks

fullWeeks = totalDays // 7
weekendDays = fullWeeks * 2


Because each full week contains exactly 2 weekend days (Saturday and Sunday).

Handle the remaining days

remainingDays = totalDays % 7
startDayOfWeek = dayOfWeek(startDate)  # 0=Monday, 6=Sunday


Loop through leftover days
For each i from 0 to remainingDays - 1:

if (startDayOfWeek + i) % 7 in {5, 6}:   # Saturday=5, Sunday=6
    weekendDays += 1


Return weekendDays

Pseudocode
function countWeekendDays(startDate, endDate):
    if startDate > endDate:
        swap(startDate, endDate)

    totalDays = (endDate - startDate).days + 1
    fullWeeks = totalDays // 7
    weekendDays = fullWeeks * 2

    remainingDays = totalDays % 7
    startDayOfWeek = dayOfWeek(startDate)  # Monday=0, Sunday=6

    for i in 0 .. remainingDays - 1:
        if ((startDayOfWeek + i) % 7) in [5, 6]:
            weekendDays += 1

    return weekendDays

Example

Range: 2025-10-01 (Wednesday) → 2025-10-18 (Saturday)

totalDays = 18

fullWeeks = 2 → 2 × 2 = 4 weekend days

remainingDays = 4 (Wed → Sat)

Wed (2), Thu (3), Fri (4), Sat (5 → weekend)

weekendDays = 4 + 1 = 5 weekend days

✅ Result: 5 weekend days
```

Q: `Math.round a decimal to the nearest hundredth in java`

A:

```
In Java, to round a decimal to the nearest hundredth (two decimal places), you can use several approaches. Here are the most common and reliable ones 👇

✅ 1. Using Math.round() with scaling

Math.round() works on whole numbers, so you multiply, round, then divide back.

double value = 3.14159;
double rounded = Math.round(value * 100.0) / 100.0;
System.out.println(rounded);  // → 3.14


Explanation:

Multiply by 100 to shift two decimal places.

Round to nearest integer.

Divide by 100.0 to shift back to two decimal places.

✅ 2. Using BigDecimal (best for precise decimal rounding)

If you’re dealing with currency or require exact decimal control, use BigDecimal.

import java.math.BigDecimal;
import java.math.RoundingMode;

double value = 3.14159;
double rounded = new BigDecimal(value)
        .setScale(2, RoundingMode.HALF_UP)
        .doubleValue();

System.out.println(rounded);  // → 3.14


Advantages:

More precise (avoids floating-point rounding issues).

Can specify rounding mode (HALF_UP, HALF_DOWN, HALF_EVEN, etc.).

✅ 3. Using String.format() (for display only)

If you just need it formatted to two decimals (not mathematically rounded for further computation):

double value = 3.14159;
System.out.println(String.format("%.2f", value));  
```