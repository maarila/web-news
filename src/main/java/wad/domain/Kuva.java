package wad.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Kuva extends AbstractPersistable<Long> {

    @OneToOne(mappedBy = "kuva")
    private Uutinen uutinen;

    @Lob
    private byte[] kuva;
}
