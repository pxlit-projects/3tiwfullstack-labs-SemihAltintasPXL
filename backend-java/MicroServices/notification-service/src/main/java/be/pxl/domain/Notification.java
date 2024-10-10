package be.pxl.domain;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sender;    // 'from' is a reserved keyword in sql
    private String receiver;
    private String subject;
    private String message;
}
