package com.perfectElegance.modal;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer chatId;
    private Integer senderId;
    private Integer recipientId;
    private String content;

    @Column(name = "`read`", nullable = false)
    private boolean read = false;

    private Date timeStamp;


}
