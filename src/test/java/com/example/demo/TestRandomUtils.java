package com.example.demo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class TestRandomUtils {

    public static Integer getRandomInt(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

    public static Short getRandomShort() {
        return Integer.valueOf(ThreadLocalRandom.current().nextInt()).shortValue();
    }

    public static BigDecimal getRandomBigDecimal() {
        return getRandomBigDecimal(1, 1000);
    }

    public static BigDecimal getRandomBigDecimal(int origin, int bound) {
        return BigDecimal.valueOf(getRandomInt(origin, bound));
    }

    public static Integer getRandomPrimaryKey() {
        return ThreadLocalRandom.current().nextInt(1, 1000000000);
    }

    public static Long getRandomLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    public static Integer getRandomPositiveNumber() {
        return ThreadLocalRandom.current().nextInt(1, 100000);
    }

    public static boolean getRandomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public static <A> A getRandomElementOfArray(A[] array) {
        int randomIndex = ThreadLocalRandom.current().nextInt(array.length);
        return array[randomIndex];
    }

    public static <T> Set<T> allEnumValuesExcluding(Class<T> enumType, T... excludedValues) {
        var allEnumValues = new HashSet<T>(Arrays.asList(enumType.getEnumConstants()));
        var excludedEnumValues = new HashSet<>(Arrays.asList(excludedValues));
        allEnumValues.removeAll(excludedEnumValues);
        return allEnumValues;
    }

    public static <T> T getRandomEnumValueExcluding(Class<T> enumType, T... excludedValues) {
        var enumValues = enumType.getEnumConstants();
        var excludedEnumValues = new HashSet<>(Arrays.asList(excludedValues));

        T randomEnumValue;
        do {
            randomEnumValue = getRandomElementOfArray(enumValues);
        }
        while (excludedEnumValues.contains(randomEnumValue));

        return randomEnumValue;
    }

    public static <A> A getRandomElementOfElements(A... array) {
        return getRandomElementOfArray(array);
    }

    public static Set<Integer> getRandomIntegerSet(int size) {
        return ThreadLocalRandom.current().ints(size)
            .boxed()
            .collect(Collectors.toSet());
    }

    public static String getRandomString(int bound) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        return ThreadLocalRandom.current().ints(leftLimit, rightLimit + 1)
            .limit(bound)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    public static LocalDateTime getRandomLocalDateTime() {
        return LocalDateTime.now().minusDays(TestRandomUtils.getRandomInt(0, 100));
    }

    public static LocalDate getRandomLocalDate(LocalDate min) {
        return min.plusDays(TestRandomUtils.getRandomInt(0, 100));
    }

    public static String getRandomEmail() {
        StringBuilder emailAddress = new StringBuilder();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        while (emailAddress.length() < 5) {
            int character = (int) (Math.random() * 26);
            emailAddress.append(alphabet.charAt(character))
                .append(Integer.valueOf((int) (Math.random() * 99)).toString());
        }
        return emailAddress.append("@altar.com.pl").toString();
    }
}
