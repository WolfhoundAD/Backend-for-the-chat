package dev.chat.entity;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class ChatParticipantId implements Serializable {

    @Column(name = "ChatID")
    private Long chatID;

    @Column(name = "ProfileID")
    private Long profileID;

}