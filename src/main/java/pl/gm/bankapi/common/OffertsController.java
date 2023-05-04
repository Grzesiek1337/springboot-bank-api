package pl.gm.bankapi.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offerts")
public class OffertsController {

    @GetMapping
    private String offerts() {
        return "offerts";
    }
}
