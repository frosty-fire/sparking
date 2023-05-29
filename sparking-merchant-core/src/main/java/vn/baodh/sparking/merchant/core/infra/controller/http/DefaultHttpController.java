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
        .setQrToken("HDBOJ5gMrWbqJIaFkUy1J9m8V8oZ2NpjduY0dA2xe+SzGscc6XFrFhMnptsrtIEsiSQhxFnojc5aUS/z+SFIkghnMrv+K7Eghs+LOFkOllNNmg1XCRm3P3DdnfTtC/iiiFSbT/x21FjJsl75S5d12I21sEpK0cDWuPY1BACw3Chv3jCupLwDYyPDNDQfCCafHA/qFSiRqmh3dM+CaRY8iRiK8lhk/7QFHME8f7iLrSYhV2N6EINn6qZMwPqF+1sPsTn1wkY5crjfT2nlGr5b5r42LAFH0N3a8MgNAmlYJgdAvAxVQsA2h1lWJi3J+vzVjmTR/LGqIL1UmbonAhU9bw==")
        .setLicensePlate("74-D1 123.46");
    parkingCoreHttpStub.submitQrParking(scanModel);
    return ResponseEntity.ok("pong");
  }
}
