package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
class PageController {
    // Fall through handler. Anything not otherwise matched, goto index.html
    @GetMapping("/**/{path:[^\\.]*}")
    public String forwardToIndex(final HttpServletRequest request) {
        return "forward:/index.html";
    }
}