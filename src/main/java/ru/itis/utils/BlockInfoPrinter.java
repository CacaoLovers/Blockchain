package ru.itis.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.models.Block;
import ru.itis.service.BlockService;

@Component
@RequiredArgsConstructor
public class BlockInfoPrinter {
    private static final String RESET = "\u001B[0m";
    private static final String[] COLORS = {
            "\u001B[31m", // Красный
            "\u001B[32m", // Зеленый
            "\u001B[33m", // Желтый
    };

    public String createBlockInfo(Block block, boolean isVerified) {
        int totalLength = 60;
        String color = isVerified ? COLORS[1] : COLORS[0];

        String topBorder = createTopBorder(totalLength, color, block.getId().toString());

        String bottomBorder = createBorder(totalLength, color);

        String message = String.format(
                "\nData1: %s\nData2: %s\nTime: %s",
                block.getData1(),
                block.getData2(),
                block.getTimestamp().toString()
        );

        return topBorder + message + "\n" + bottomBorder;
    }

    private String createTopBorder(int length, String color, String blockId) {
        StringBuilder border = new StringBuilder(color);
        String blockInfo = "[ Block " + blockId + " ]";
        int paddingLength = (length - blockInfo.length()) / 2;

        border.append("=".repeat(Math.max(0, paddingLength)));
        border.append(COLORS[2]).append(blockInfo).append(color);
        border.append("=".repeat(Math.max(0, paddingLength)));

        if ((length - blockInfo.length()) % 2 != 0) {
            border.append("=");
        }

        border.append(RESET);
        return border.toString();
    }

    private String createBorder(int length, String color) {
        StringBuilder border = new StringBuilder(color);
        for (int i = 0; i < length; i++) {
            border.append("=");
        }
        border.append("\n");
        border.append(RESET);
        return border.toString();
    }
}

