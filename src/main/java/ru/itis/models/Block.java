package ru.itis.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Block {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    private byte[] previousHash;
    private String data1;
    private String data2;
    private byte[] dataSignature;
    private byte[] blockSignature;

    @Override
    public String toString() {
        return "{" +
                "id = " + id.toString() +
                "previousHash = " + previousHash +
                "data1 = " + data1 +
                "data2 = " + data2 +
                "timestamp = " + timestamp.toString() +
                "} ";
    }
}
