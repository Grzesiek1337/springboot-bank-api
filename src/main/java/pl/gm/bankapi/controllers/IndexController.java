package pl.gm.bankapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.gm.bankapi.repositories.FakeStandardAccountRepo;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private FakeStandardAccountRepo fakeStandardAccountRepo;

    @GetMapping
    @ResponseBody
    public String startIndex() {
         return fakeStandardAccountRepo.getAccounts().toString();
    }
}
