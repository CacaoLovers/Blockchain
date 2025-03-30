package ru.itis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignatureDto {
    private String publicKey;
    private String privateKey;
    private String algorithm;
}
