package ru.itis.ui;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.itis.dto.BlockDto;
import ru.itis.models.Block;
import ru.itis.models.BlockChain;
import ru.itis.models.ui.Menu;
import ru.itis.service.BlockChainService;
import ru.itis.service.BlockService;
import ru.itis.utils.LineCommand;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import static ru.itis.utils.LineCommand.*;
import static ru.itis.utils.CommandLineUtils.findCommand;

@Component
@RequiredArgsConstructor
public class BlockChainUI {
    private final BlockService blockService;
    private final BlockChainService blockChainService;
    private final Scanner scanner = new Scanner(System.in);

    @SneakyThrows
    public void render() {
        printMenu();
        LineCommand command = findCommand(scanner.next().charAt(0));
        while (command != MENU_EXIT) {
            if (FIRST_COMMAND.equals(command)) {
                System.out.println("Введите индентификатор блокчейна:");
                String blockChainName = scanner.next();
                blockChainService.createBlockChain(blockChainName);
            } else if (SECOND_COMMAND.equals(command)) {
                System.out.println("Введите индентификатор блокчейна:");
                String blockChainName = scanner.next();
                Optional<BlockChain> blockChain = blockChainService.loadBlockChain(blockChainName);

                blockChain.ifPresent((bc) -> {
                    BlockDto blockDto = BlockDto.builder()
                            .data1("dc")
                            .data2("fd")
                            .build();
                    Block block = blockService.createBlock(blockDto);
                    blockChainService.addBlock(bc, block);
                    blockChainService.printBlockChainInfo(bc);
                });
            } else if (THIRD_COMMAND.equals(command)) {
                System.out.println("Введите индентификатор блокчейна:");
                String blockChainName = scanner.next();
                Optional<BlockChain> blockChain = blockChainService.loadBlockChain(blockChainName);
                blockChain.ifPresent(blockChainService::printBlockChainInfo);
            } else if (FOURTH_COMMAND.equals(command)) {
                break;
            }
            printMenu();
            command = findCommand(scanner.next().charAt(0));
        }
    }

    private void printMenu() {
        Menu menu = Menu.builder()
                .title("Выберите действие с блокчейном:")
                .options(Map.of(
                        FIRST_COMMAND, "Создать новый блокчейн",
                        SECOND_COMMAND, "Добавить блок в блокчейн",
                        THIRD_COMMAND, "Посмотреть блокчейн",
                        FOURTH_COMMAND, "Проверить целостность",
                        MENU_EXIT, "Выйти в главное меню"
                ))
                .build();

        System.out.println(menu);
    }
}
