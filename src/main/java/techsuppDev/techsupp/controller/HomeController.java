package techsuppDev.techsupp.controller;

import lombok.RequiredArgsConstructor;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import techsuppDev.techsupp.config.UserDetailsimpl;
import techsuppDev.techsupp.controller.form.UserForm;
import techsuppDev.techsupp.domain.User;
import techsuppDev.techsupp.service.AdminProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class HomeController {

    @GetMapping("/")
    public String main() {
        return "/main/main";
    }

}
