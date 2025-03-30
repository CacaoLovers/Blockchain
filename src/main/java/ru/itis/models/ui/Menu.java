package ru.itis.models.ui;

import lombok.Builder;
import lombok.Data;
import ru.itis.utils.LineCommand;

import java.util.Map;
import java.util.StringJoiner;

@Data
@Builder
public class Menu {
    private String title;
    private Map<LineCommand, String> options;

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("=".repeat(40));
        stringJoiner.add(title);
        stringJoiner.add(
            options.entrySet().stream()
                .map((option) -> "\t" + option.getKey().getSymbol() + " - " + option.getValue())
                .sorted()
                .reduce((k, v) -> k + "\n" + v).orElse(null)
        );
        stringJoiner.add("=".repeat(40));

        return stringJoiner.toString();
    }
}
