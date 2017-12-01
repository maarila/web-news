package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String list(Model model) {
        model.addAttribute("uutiset", uutisRepository.findAll());
        return "index";
    }

    @PostMapping("/")
    public String create(@RequestParam String otsikko) {
        Uutinen uutinen = new Uutinen();
        uutinen.setOtsikko(otsikko);
        uutisRepository.save(uutinen);
        return "redirect:/";
    }
}
