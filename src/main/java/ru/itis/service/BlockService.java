package ru.itis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dto.BlockDto;
import ru.itis.models.Block;
import ru.itis.utils.BlockInfoPrinter;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockInfoPrinter blockInfoPrinter;
    private final SignatureService signatureService;

    public Block createBlock(BlockDto blockDto) {

        return Block.builder()
                .data1(blockDto.getData1())
                .data2(blockDto.getData2())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public boolean verifyBlock(Block block) {
        return block.getId() % 2 == 1;
    }

    public String createBlockInfo(Block block) {
        return blockInfoPrinter.createBlockInfo(block, verifyBlock(block));
    }
}
