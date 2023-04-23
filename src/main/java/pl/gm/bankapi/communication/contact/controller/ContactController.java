package pl.gm.bankapi.communication.contact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gm.bankapi.communication.contact.dto.ContactDto;
import pl.gm.bankapi.communication.contact.service.ContactService;

import javax.validation.Valid;

@Controller
@RequestMapping("contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/new")
    public String showContactForm(Model model) {
        model.addAttribute("contact", new ContactDto());
        return "contact/new";
    }

    @PostMapping("/new")
    public String submitContactForm(
            @Valid @ModelAttribute("contact") ContactDto contactDto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Invalid contact data");
            model.addAttribute("contact", contactDto);
            return "contact/new";
        }
        contactService.saveContact(contactDto);
        return "redirect:/";
    }
}
