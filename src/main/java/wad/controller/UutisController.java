package wad.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Kirjoittaja;
import wad.domain.Kuva;
import wad.domain.Uutinen;
import wad.repository.KategoriaRepository;
import wad.repository.KirjoittajaRepository;
import wad.repository.KuvaRepository;
import wad.repository.UutisRepository;
import wad.service.Muokkaus;

@Controller
public class UutisController {

    @Autowired
    private UutisRepository uutisRepository;

    @Autowired
    private KirjoittajaRepository kirjoittajaRepository;

    @Autowired
    private KategoriaRepository kategoriaRepository;

    @Autowired
    private KuvaRepository kuvaRepository;

    private final Muokkaus muokkaus = new Muokkaus();

    @GetMapping("/")
    public String etusivu(Model model) {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "julkaisuaika");
        model.addAttribute("uutiset", uutisRepository.findAll(pageable));
        return "index";
    }

    @GetMapping("/uusimmat")
    public String uusimmatLista(Model model) {
        Sort sort = new Sort(Sort.Direction.DESC, "julkaisuaika");
        model.addAttribute("uutiset", uutisRepository.findAll(sort));
        model.addAttribute("listaus", "Uusimmat uutiset");
        return "uutislista";
    }

    @GetMapping("/luetuimmat")
    public String luetuimmatLista(Model model) {
        Sort sort = new Sort(Sort.Direction.DESC, "luettu");
        model.addAttribute("uutiset", uutisRepository.findAll(sort));
        model.addAttribute("listaus", "Luetuimmat uutiset");
        return "uutislista";
    }

    @GetMapping("/uutinen/{id}")
    public String uutinen(@PathVariable Long id, Model model) {
        Uutinen uutinen = this.uutisRepository.getOne(id);
        uutinen.setLuettu(uutinen.getLuettu() + 1);
        this.uutisRepository.save(uutinen);
        model.addAttribute("uutinen", uutinen);
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "julkaisuaika");
        model.addAttribute("uusimmat", uutisRepository.findAll(pageable));
        return "uutinen";
    }

    @GetMapping("/hallinta")
    public String hallintapaneeli(Model model, HttpSession session) {
        if (!(session.getAttribute("admin") == null)) {
            if (session.getAttribute("admin").equals("onSeAdmin")) {
                return "hallintapaneeli";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/kirjautuminen")
    public String kirjautuminen(HttpSession session) {
        if (!(session.getAttribute("admin") == null)) {
            if (session.getAttribute("admin").equals("onSeAdmin")) {
                return "hallintapaneeli";
            }
        }
        return "kirjautuminen";
    }

    @PostMapping("/kirjautuminen")
    public String kirjaudu(@RequestParam String kayttajatunnus, @RequestParam String salasana, HttpSession session) {
        if (kayttajatunnus.equals("admin") && salasana.equals("adminlol")) {
            session.setAttribute("admin", "onSeAdmin");
            return "hallintapaneeli";
        }
        return "redirect:/kirjautuminen";
    }

    @PostMapping("/hallinta")
    public String luoUutinen(@RequestParam String otsikko, @RequestParam String ingressi,
            @RequestParam String leipateksti, @RequestParam String julkaisuaika,
            @RequestParam String kirjoittajat, @RequestParam String kategoriat,
            @RequestParam("kuva") MultipartFile tiedosto) throws IOException {

        Uutinen uutinen = new Uutinen();
        uutinen.setOtsikko(otsikko);
        uutinen.setIngressi(ingressi);
        uutinen.setLeipateksti(leipateksti);
        uutinen.setJulkaisuaika(muokkaus.luoOikeanMuotoinenAika(julkaisuaika));
        uutinen.setLuettu(0);

        String[] kirjoittajaTaulukko = muokkaus.erotaToisistaan(kirjoittajat);
        List<Kirjoittaja> uutisenKirjoittajat = new ArrayList<>();

        for (int i = 0; i < kirjoittajaTaulukko.length; i++) {
            Kirjoittaja kirjoittaja = kirjoittajaRepository.findByNimi(kirjoittajaTaulukko[i]);
            if (kirjoittaja == null) {
                Kirjoittaja uusi = new Kirjoittaja();
                uusi.setNimi(kirjoittajaTaulukko[i]);
                uusi.setUutiset(new ArrayList<>());
                kirjoittajaRepository.save(uusi);
                kirjoittaja = kirjoittajaRepository.findByNimi(kirjoittajaTaulukko[i]);
            }
            uutisenKirjoittajat.add(kirjoittaja);
            kirjoittaja.getUutiset().add(uutinen);
            kirjoittajaRepository.save(kirjoittaja);
        }
        uutinen.setKirjoittajat(uutisenKirjoittajat);

        Kuva kuva = new Kuva();
        kuva.setKuva(tiedosto.getBytes());
        kuvaRepository.save(kuva);
        uutinen.setKuva(kuva);
//        uutinen.getKategoriat().add(kategoria);
//        Kategoria kategoria = new Kategoria();
//        kategoria.setName(kategoriat);
        uutisRepository.save(uutinen);
        return "redirect:/hallinta";
    }

    @GetMapping(path = "/uutinen/{id}", produces = "image/jpg")
    @ResponseBody
    public byte[] get(@PathVariable Long id) {
        return this.kuvaRepository.findById(id).get().getKuva();
    }
}
