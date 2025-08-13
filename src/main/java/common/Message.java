package common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private String sender;
    private String text;
    private String timestamp;
}
