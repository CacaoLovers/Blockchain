package ru.itis.utils;

import java.util.Arrays;

public class CommandLineUtils {
    private static final String[] COLORS = {
            "\u001B[31m", // Красный
            "\u001B[32m", // Зеленый
            "\u001B[33m", // Желтый
    };

    public static LineCommand findCommand(char inputChar) {
        return Arrays.stream(LineCommand.values())
                .filter(lc -> lc.getSymbol() == inputChar)
                .findFirst()
                .orElse(LineCommand.MENU);
    }

    public static void printMenu(String title, String menu) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("=".repeat(40));
        stringBuilder.append("\n");
        stringBuilder.append(title);
        stringBuilder.append("\n");
        stringBuilder.append("" + menu);
        stringBuilder.append("\n");
        stringBuilder.append("=".repeat(40));
        stringBuilder.append("\n");
        System.out.println(stringBuilder);
    }
}
