package wad.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import wad.domain.Kuva;
import wad.domain.Uutinen;
import wad.repository.KuvaRepository;
import wad.repository.UutisRepository;

@Controller
public class UutisController {

    @Autowired
    private UutisRepository uutisRepository;

    @Autowired
    private KuvaRepository kuvaRepository;

    @GetMapping("/")
    public String etusivu(Model model) {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "julkaisuaika");
        model.addAttribute("uutiset", uutisRepository.findAll(pageable));
        return "index";
    }

    @GetMapping("/uusimmat")
    public String uusinLista(Model model) {
        Sort sort = new Sort(Sort.Direction.DESC, "julkaisuaika");
        model.addAttribute("uutiset", uutisRepository.findAll(sort));
        return "uutislista";
    }

    @GetMapping("/luetuimmat")
    public String luetuimmatLista(Model model) {
        return "uutislista";
    }

    @GetMapping("/uutinen/{id}")
    public String uutinen(@PathVariable Long id, Model model) {
        Uutinen uutinen = this.uutisRepository.getOne(id);
        uutinen.setLuettu(uutinen.getLuettu() + 1);
        this.uutisRepository.save(uutinen);
        model.addAttribute("uutinen", uutinen);
        return "uutinen";
    }

    @GetMapping("/hallinta")
    public String hallintapaneeli(Model model) {
        model.addAttribute("uutiset", uutisRepository.findAll());
        return "hallintapaneeli";
    }

    @GetMapping("/kirjautuminen")
    public String kirjautuminen() {
        return "kirjautuminen";
    }

    @PostMapping("/hallinta")
    public String luoUutinen(@RequestParam String otsikko, @RequestParam String ingressi,
            @RequestParam String leipateksti, @RequestParam String julkaisuaika,
            @RequestParam String kirjoittajat, @RequestParam String kategoriat)
            throws IOException {
        Uutinen uutinen = new Uutinen();

//        Kirjoittaja kirjoittaja = new Kirjoittaja();
//        kirjoittaja.setName(kirjoittajat);
//        Kategoria kategoria = new Kategoria();
//        kategoria.setName(kategoriat);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(julkaisuaika, formatter);

        uutinen.setOtsikko(otsikko);
        uutinen.setIngressi(ingressi);
        uutinen.setLeipateksti(leipateksti);
        uutinen.setJulkaisuaika(dateTime);
        uutinen.setLuettu(0);
//        uutinen.getKirjoittajat().add(kirjoittaja);
//        uutinen.getKategoriat().add(kategoria);
        uutisRepository.save(uutinen);
        return "redirect:/hallinta";
    }

    @PostMapping("/hallinta/kuva")
    public String lisaaKuva(@RequestParam Long uutisId, @RequestParam("kuva") MultipartFile tiedosto) throws IOException {
        Kuva kuva = new Kuva();
        kuva.setKuva(tiedosto.getBytes());
        kuvaRepository.save(kuva);
//        if (kuva.getContentType().contains("image/")) {
//            uutinen.setKuva(kuva.getBytes());
//        }        
        Uutinen uutinen = uutisRepository.getOne(uutisId);
        uutinen.setKuva(kuva);
        uutisRepository.save(uutinen);

        return "redirect:/hallinta";
    }

    @GetMapping(path = "/uutinen/{id}", produces = "image/jpg")
    @ResponseBody
    public byte[] get(@PathVariable Long id) {
        return this.kuvaRepository.findById(id).get().getKuva();
    }
}
