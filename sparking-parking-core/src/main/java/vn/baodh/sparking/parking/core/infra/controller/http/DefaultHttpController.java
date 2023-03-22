package vn.baodh.sparking.parking.core.infra.controller.http;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prc")
public class DefaultHttpController {
  @GetMapping("/ping")
  public ResponseEntity<?> ping() {
    return ResponseEntity.ok("pong");
  }
}
