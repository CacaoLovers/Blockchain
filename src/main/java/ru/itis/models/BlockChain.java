package ru.itis.models;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockChain {
    private String id;
    private List<Block> blocks;
}
