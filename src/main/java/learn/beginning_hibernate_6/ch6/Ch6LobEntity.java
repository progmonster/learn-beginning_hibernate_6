package learn.beginning_hibernate_6.ch6;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
public class Ch6LobEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private String textContent;

    @Lob
    @Basic(fetch = LAZY)
    private byte[] primitiveByteContent;

    @Lob
    @Basic(fetch = LAZY)
    private Byte[] byteContent;

    public Ch6LobEntity(String textContent, byte[] primitiveByteContent, Byte[] byteContent) {
        this.textContent = textContent;
        this.primitiveByteContent = primitiveByteContent;
        this.byteContent = byteContent;
    }
}
