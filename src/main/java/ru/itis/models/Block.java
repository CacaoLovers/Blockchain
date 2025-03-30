package ru.itis.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ext.JodaDeserializers;
import org.codehaus.jackson.map.ext.JodaSerializers;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
@Builder
public class Block {
    private Long id;
    private byte[] previousHash;
    private String data1;
    private String data2;
    private byte[] dataSignature;
    private byte[] blockSignature;
    @JsonFormat(pattern = "yy")
    private LocalDateTime timestamp;

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
