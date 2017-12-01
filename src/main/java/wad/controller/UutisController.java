package wad.controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Uutinen;
import wad.repository.UutisRepository;

@Controller
public class UutisController {

    @Autowired
    private UutisRepository uutisRepository;

    @GetMapping("/")
    public String etusivu(Model model) {
        // etusivu listaa oletuksena viisi uusinta uutista
        model.addAttribute("uutiset", uutisRepository.findAll());
        return "index";
    }

    @GetMapping("/hallinta")
    public String hallintapaneeli() {
        return "hallintapaneeli";
    }

    @PostMapping("/hallinta")
        public String luoUutinen(@RequestParam String otsikko, @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM") LocalDateTime aika) {
        Uutinen uutinen = new Uutinen();
        uutinen.setOtsikko(otsikko);
        uutisRepository.save(uutinen);
        return "redirect:/";
    }
}
