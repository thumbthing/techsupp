package techsuppDev.techsupp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedbackMain")
public class FeedbackController {



    @GetMapping("/*")
    public ModelAndView LinkToFeedbackMain() {
        ModelAndView feedbackMain = new ModelAndView("/feedback/feedbackmain");
        return feedbackMain;
    }


}
