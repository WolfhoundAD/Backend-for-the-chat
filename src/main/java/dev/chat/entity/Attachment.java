package dev.chat.entity;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private Message message;

    @Column(name = "file_path")
    private String filePath;
}
   // todo апликейшен убнрат, оставить докер компос. Депенд он в докере. ПОчитать зачем
    //локал хост. Network почитать. Как это работает. Правила гет пост, что можно предавать
    //в тело запроса. Протестировать @transction чтобы и в бд откатывалось