package wad.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Kuva;

public class Muokkaus {

    public String[] erotaToisistaan(String erotettavaMerkkijono) {
        String[] palaset = erotettavaMerkkijono.trim().split(", ");
        return palaset;
    }

    public LocalDateTime luoOikeanMuotoinenAika(String julkaisuaika) {
        DateTimeFormatter muotoilija = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime aika = LocalDateTime.parse(julkaisuaika, muotoilija);
        return aika;
    }

    public Kuva luoKuva(MultipartFile tiedosto) throws IOException {
        Kuva kuva = new Kuva();
        kuva.setKuva(tiedosto.getBytes());
        return kuva;
    }
}
