package techsuppDev.techsupp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class SpecificProductController {
    @GetMapping("productSelect/product")
    public ModelAndView LinkToSpecificProduct() {
        ModelAndView specific = new ModelAndView("/product/productSelect");
        return specific;
    }

    @GetMapping("feedbackSelect/feedback")
    public ModelAndView LinkToSpecificFeedback() {
        ModelAndView specific = new ModelAndView("/feedback/feedbackselect");
        return specific;
    }

    @GetMapping("feedbackSelect/feedback/form/*")
    public ModelAndView LinkToFeedbackForm() {
        ModelAndView specific = new ModelAndView("/feedback/feedbackwrite");
        return specific;
    }


}
