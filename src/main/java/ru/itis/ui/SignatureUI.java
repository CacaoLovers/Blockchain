package ru.itis.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.dto.SignatureDto;
import ru.itis.models.ui.Menu;
import ru.itis.service.SignatureService;
import ru.itis.utils.LineCommand;

import java.security.KeyPair;
import java.util.Map;
import java.util.Scanner;

import static java.util.Objects.nonNull;
import static ru.itis.utils.LineCommand.*;
import static ru.itis.utils.CommandLineUtils.findCommand;

@Component
@RequiredArgsConstructor
public class SignatureUI {
    private final SignatureService signatureService;
    private final Scanner scanner = new Scanner(System.in);

    public void render() {
        showSignatureMenu();
        LineCommand command = findCommand(scanner.next().charAt(0));
        while (!MENU_EXIT.equals(command)) {
            if (FIRST_COMMAND.equals(command)) {
                createKeyPair();
            } else if (SECOND_COMMAND.equals(command)) {
                signatureService.decryptPair("Слово");
            }
            showSignatureMenu();
            command = findCommand(scanner.next().charAt(0));
        }
    }
    
    private void createKeyPair() {
        SignatureDto signatureDto = SignatureDto.builder()
                .privateKey("private.key")
                .publicKey("public.key")
                .algorithm("RSA")
                .build();
        KeyPair keyPair = signatureService.createKeyPair(signatureDto);
        if (nonNull(keyPair)) {
            System.out.println("Пара ключей была успешно создана\n" +
                    "private key: " + signatureDto.getPrivateKey() + "\n" +
                    "public key: " + signatureDto.getPublicKey() + "\n"
            );
        } else {
            System.out.println("Не удалось создать пару ключей.");
        }
    }
    
    private void showSignatureMenu() {
        Menu menu = Menu.builder()
                .title("Выберите действие с блокчейном:")
                .options(Map.of(
                        FIRST_COMMAND, "Сохранить новую пару ключей",
                        SECOND_COMMAND, "Посмотреть текущую пару",
                        MENU_EXIT, "Выйти в главное меню"
                    )
                )
                .build();
        System.out.println(menu);
    }
}
