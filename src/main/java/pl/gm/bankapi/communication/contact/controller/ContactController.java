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
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/new")
    public String showContactForm(Model model) {
        model.addAttribute("contact", new ContactDto());
        return "communication/contact/new";
    }

    @PostMapping("/new")
    public String submitContactForm(
            @Valid @ModelAttribute("contact") ContactDto contactDto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Invalid contact data");
            model.addAttribute("contact", contactDto);
            return "communication/contact/new";
        }
        contactService.createContact(contactDto);
        return "redirect:/";
    }

    @GetMapping("/all-unanswered")
    public String getAllUnansweredContacts(Model model) {
        model.addAttribute("unansweredContactsDto", contactService.findAllUnansweredContacts());
        return "communication/contact/list-unanswered";
    }

    @GetMapping("/all-answered")
    public String getAllAnsweredContacts(Model model) {
        model.addAttribute("answeredContactsDto", contactService.findAllAnsweredContacts());
        return "communication/contact/list-answered";
    }
}
