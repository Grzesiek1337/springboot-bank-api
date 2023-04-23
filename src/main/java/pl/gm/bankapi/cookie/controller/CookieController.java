package pl.gm.bankapi.cookie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CookieController {

    @GetMapping("/cookie-consent")
    public String cookieConsent() {
        return "cookie-consent";
    }

    @PostMapping("/cookie-consent")
    public String postControler(@RequestParam(name = "cookie-consent", required = false) String consent, HttpServletResponse response) {
        if (consent == null || !consent.equals("true")) {
            return "error-page";
        }
        Cookie cookie = new Cookie("cookie-consent", "true");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
