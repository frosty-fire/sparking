package vn.baodh.sparking.merchant.core.infra.controller.http;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.baodh.sparking.merchant.core.domain.model.ScanModel;
import vn.baodh.sparking.merchant.core.infra.client.ParkingCoreHttpStub;

@Slf4j
@RestController
@RequestMapping("/mrc")
@RequiredArgsConstructor
public class DefaultHttpController {

  private final ParkingCoreHttpStub parkingCoreHttpStub;

  @GetMapping("/ping")
  public ResponseEntity<?> ping(
      HttpServletRequest uri,
      @RequestParam Map<String, String> params
  ) {
    return ResponseEntity.ok("pong");
  }
}
