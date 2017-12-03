
package wad.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import wad.repository.KirjoittajaRepository;

public class Muokkaus {
    
    @Autowired
    KirjoittajaRepository kirjoittajaRepository;
    
    public String[] erotaToisistaan(String erotettavaMerkkijono) {
        String[] palaset = erotettavaMerkkijono.trim().split(", ");
        
        return palaset;
    }

    public LocalDateTime luoOikeanMuotoinenAika(String julkaisuaika) {
        DateTimeFormatter muotoilija = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime aika = LocalDateTime.parse(julkaisuaika, muotoilija);
        return aika;
    }

}
