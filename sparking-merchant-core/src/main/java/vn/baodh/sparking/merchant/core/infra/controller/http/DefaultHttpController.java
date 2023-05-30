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
    var scanModel = new ScanModel()
        .setQrToken("EEpNxUIO3tgKHfmmgZknMq2Hfe9LJD2cUBdTXkJ9o05ZcrmHDr4KuvkoPB0wvYNn2eOhBI8fDCcntqVhGSx+82gc6fuMQ1GVw5RUQqs7r2yfEVmzplTCWsPQROMEQvyGEiNVQJ+czUddSiMFYXJCKc2J02bSTBDfMysGIz6IjB5yOLHrxMnFvP8Pm7O6Ttv7ZYSwVy1ET9CAJxfk2T2WxOoqVYZJb6ncsDEuxKxgPt48sW99IFFqdiLKUvRY7s0AP7wbE+pZc0wjmOVp07T3Y/i62ZQjEU8clyz9wj9Q3BUGp/q/xUQ01AUHSZe6tpxY2U0w/ouJsucFqH1QnzzSag==")
        .setLicensePlate("74-D1 123.46");
    parkingCoreHttpStub.submitQrParking(scanModel);
    return ResponseEntity.ok("pong");
  }
}
