package ru.itis.utils;

import lombok.Data;
import lombok.Getter;

public enum LineCommand {
    MENU_EXIT('e'),
    FIRST_COMMAND('1'),
    SECOND_COMMAND('2'),
    THIRD_COMMAND('3'),
    FOURTH_COMMAND('4'),
    MENU('m');

    private final char symbol;
    LineCommand(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
