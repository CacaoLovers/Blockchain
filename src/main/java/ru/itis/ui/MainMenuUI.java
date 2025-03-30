package ru.itis.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.models.ui.Menu;
import ru.itis.utils.LineCommand;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import static ru.itis.utils.LineCommand.*;

@Component
@RequiredArgsConstructor
public class MainMenuUI {
    private final SignatureUI signatureUI;
    private final BlockChainUI blockChainUI;

    private final Scanner scanner = new Scanner(System.in);

    public void render() {
        showMenu();
        LineCommand command = findCommand(scanner.next().charAt(0));
        while (command != MENU_EXIT) {
            if (FIRST_COMMAND.equals(command)) {
                signatureUI.render();
            } else if (SECOND_COMMAND.equals(command)) {
                blockChainUI.render();
            }
            showMenu();
            command = findCommand(scanner.next().charAt(0));
        }
    }

    private void showMenu() {
        Menu menu = Menu.builder()
                .title("Выберите действие:")
                .options(Map.of(
                    FIRST_COMMAND, "Работа с подписью",
                    SECOND_COMMAND, "Работа с блокчейном",
                    MENU_EXIT, "Выйти из программы"
                ))
                .build();

        System.out.println(menu);
    }

    private LineCommand findCommand(char inputChar) {
        return Arrays.stream(LineCommand.values())
                .filter(lc -> lc.getSymbol() == inputChar)
                .findFirst()
                .orElse(LineCommand.MENU);
    }
}
