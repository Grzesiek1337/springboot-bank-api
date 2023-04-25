package pl.gm.bankapi.communication.feedback.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.gm.bankapi.communication.contact.dto.ContactDto;
import pl.gm.bankapi.communication.contact.service.ContactService;
import pl.gm.bankapi.communication.feedback.ContactFeedbackDto;
import pl.gm.bankapi.communication.feedback.service.FeedBackService;
import pl.gm.bankapi.user.currentuser.CurrentUserDetails;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/feedback")
public class FeedBackController {

    private final FeedBackService feedBackService;
    private final ContactService contactService;

    public FeedBackController(FeedBackService feedBackService, ContactService contactService) {
        this.feedBackService = feedBackService;
        this.contactService = contactService;
    }

    /**
     * Displays feedback form for a specific contact.
     * @param contactId          the ID of the contact
     * @param model              the model used to pass data to the view
     * @param currentUserDetails the details of the currently authenticated user
     * @return the name of the view to display the feedback form
     */
    @GetMapping("/new/{contactId}")
    public String showFeedbackContactForm(@PathVariable("contactId") Long contactId,
                                          Model model,
                                          @AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        model.addAttribute("contactDto", contactService.getContactById(contactId));
        ContactFeedbackDto contactFeedbackDto = new ContactFeedbackDto();
        contactFeedbackDto.setAuthor(currentUserDetails.getUsername());
        model.addAttribute("contactFeedbackDto", contactFeedbackDto);
        return "communication/feedback/new";
    }

    /**
     * Handles submission of the feedback form for a specific contact.
     * @param contactFeedbackDto the feedback DTO object
     * @param result             the binding result
     * @param contactId          the ID of the contact
     * @return the response string
     */
    @PostMapping("/new")
    @ResponseBody
    public String submitFeedbackContact(@Valid ContactFeedbackDto contactFeedbackDto,
                                        BindingResult result,
                                        @RequestParam("contactId") Long contactId) {
        if (result.hasErrors()) {
            return "communication/feedback/new";
        }

        ContactDto contact = contactService.getContactById(contactId);
        contact.setResponseProvided(true);
        contactService.updateExistingContact(contact);

        contactFeedbackDto.setResponseTime(LocalDateTime.now());
        contactFeedbackDto.setContact(contact);
        feedBackService.saveFeedback(contactFeedbackDto);

        //TODO Implement email sender

        return contactFeedbackDto.toString();
    }
}