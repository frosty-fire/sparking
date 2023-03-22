package vn.baodh.sparking.um.authorization.infra.controller.http;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/um")
public class DefaultHttpController {
  @GetMapping("/ping")
  public ResponseEntity<?> ping() {
    return ResponseEntity.ok("pong");
  }
}
