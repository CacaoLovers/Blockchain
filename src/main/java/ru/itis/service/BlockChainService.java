package ru.itis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.models.Block;
import ru.itis.models.BlockChain;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockChainService {
    private static final AtomicLong blockId = new AtomicLong(0);
    private final BlockService blockService;
    private final ObjectMapper objectMapper;

    public BlockChain createBlockChain(String blockChainName) {
        try {
            BlockChain blockChain = BlockChain.builder()
                    .id(blockChainName)
                    .blocks(new ArrayList<>())
                    .build();
            objectMapper.writeValue(new File(blockChainName + ".json"), blockChain);
            return blockChain;
        } catch (FileAlreadyExistsException ex) {
            log.error("Данный блокчейн уже существует");
            return null;
        } catch (IOException ex) {
            log.error("Не удалость создать новый блокчейн");
            return null;
        }
    }

    public Optional<BlockChain> loadBlockChain(String blockChainName) {
        try {
            return Optional.ofNullable(
                    objectMapper.readValue(new File(blockChainName + ".json"), BlockChain.class)
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            log.error("Не удалось загрузить блокчейн с таким индентификатором.");
            return Optional.empty();
        }
    }

    public Optional<BlockChain> saveBlockChain(String blockChainName, BlockChain blockChain) {
        try {
            objectMapper.writeValue(new File(blockChainName + ".json"), blockChain);
            return Optional.of(blockChain);
        } catch (IOException e) {
            log.error("Не удалось загрузить блокчейн с таким индентификатором.");
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public BlockChain addBlock(BlockChain blockChain, Block block) {
        block.setId(blockId.incrementAndGet());
        blockChain.getBlocks().add(block);
        return saveBlockChain(blockChain.getId(), blockChain).orElse(blockChain);
    }

    public boolean verifyBlockChain(BlockChain blockChain) {
        return blockChain.getBlocks().stream()
                .allMatch(blockService::verifyBlock);
    }

    public void printBlockChainInfo(BlockChain blockChain) {
        blockChain.getBlocks().stream()
                .map(blockService::createBlockInfo)
                .forEach(System.out::println);
    }
}
