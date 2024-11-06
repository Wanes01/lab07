package it.unibo.nestedenum;

import java.util.*;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    private enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private final int days;

        private Month(final int days) {
            this.days = days;
        }

        public static Month fromString(final String name) {
            final Queue<Character> toMatch = stringToLowercaseCharQueue(
                    Objects.requireNonNull(name, "Month name must not be null"));
            final Map<Month, Queue<Character>> possibleMatches = mapMonthsToCharQueue();

            /*
             * Tries matching character by character from name
             * with the characters from the month names
             */
            while (!toMatch.isEmpty()) {
                final char letterToMatch = toMatch.remove();
                final var it = possibleMatches.keySet().iterator();
                // Removes all the non matching months name
                while (it.hasNext()) {
                    final Month month = it.next();
                    if (possibleMatches.get(month).peek() == null
                            || letterToMatch != possibleMatches.get(month).remove()) {
                        it.remove();
                    }
                }
            }

            if (possibleMatches.size() > 1) {
                String names = "";
                for (final var month : possibleMatches.keySet()) {
                    names += month.name() + " ";
                }
                throw new IllegalArgumentException(
                        "Month name is ambiguous. Did you mean any of the following? " + names);
            } else if (possibleMatches.size() < 1) {
                throw new IllegalArgumentException("No month matches " + name);
            }

            return new ArrayList<>(possibleMatches.keySet()).get(0);
        }

        private static Queue<Character> stringToLowercaseCharQueue(final String string) {
            final Queue<Character> chars = new LinkedList<>();
            for (final var c : string.toLowerCase().toCharArray()) {
                chars.add(c);
            }
            return chars;
        }

        private static Map<Month, Queue<Character>> mapMonthsToCharQueue() {
            final Map<Month, Queue<Character>> mout = new HashMap<>();
            for (final var month : Month.values()) {
                mout.put(month, stringToLowercaseCharQueue(month.name()));
            }
            return mout;
        }
    }

    @Override
    public Comparator<String> sortByDays() {
        return new Comparator<String>() {
            @Override
            public int compare(String month1, String month2) {
                final Month first = Month.fromString(Objects.requireNonNull(month1));
                final Month second = Month.fromString(Objects.requireNonNull(month2));
                return first.days - second.days;
            }
        };
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new Comparator<String>() {
            @Override
            public int compare(String month1, String month2) {
                final Month first = Month.fromString(Objects.requireNonNull(month1));
                final Month second = Month.fromString(Objects.requireNonNull(month2));
                return first.ordinal() - second.ordinal();
            }
        };
    }
}
