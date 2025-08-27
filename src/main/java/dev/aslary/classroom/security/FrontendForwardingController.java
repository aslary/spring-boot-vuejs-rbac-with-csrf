package dev.aslary.classroom.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendForwardingController {

  @GetMapping("{path:^(?!api|assets)[^\\.]*}/**")
  public String handleForward() {
    return "forward:/";
  }
}
