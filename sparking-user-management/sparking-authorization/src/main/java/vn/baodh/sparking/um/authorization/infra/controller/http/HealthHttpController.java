package vn.baodh.sparking.um.authorization.infra.controller.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthHttpController {

  @GetMapping(value = {"/info", "/health"})
  public ResponseEntity<?> healthCheck() {
    return ResponseEntity.status(HttpStatus.OK).body(1);
  }

}