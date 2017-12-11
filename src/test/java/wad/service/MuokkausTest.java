package wad.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MuokkausTest {

    private Muokkaus muokkaus;
    private String erotettava;

    @Before
    public void setUp() {
        this.muokkaus = new Muokkaus();
        this.erotettava = "eka, toka, kolmas";
    }

    @Test
    public void erottaaMerkkijonotToisistaanOikein() {
        String[] erotettuna = muokkaus.erotaToisistaan(erotettava);
        assertEquals(3, erotettuna.length);
    }

    @Test
    public void erottaminenPoistaaPilkut() {
        String[] erotettuna = muokkaus.erotaToisistaan(erotettava);
        assertFalse("eka, ".equals(erotettuna[0]));

    }

    @Test
    public void muokkaaHTMLDateTimeLocalinJavanLocalDateTimeksi() {
        String dateTimeLocal = "2017-12-11T11:10";
        DateTimeFormatter muotoilija = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime aika = LocalDateTime.parse(dateTimeLocal, muotoilija);
        assertEquals(dateTimeLocal, aika.toString());
    }

}
