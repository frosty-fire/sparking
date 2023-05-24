package vn.baodh.sparking.merchant.core.infra.controller.http;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.baodh.sparking.merchant.core.domain.enumeration.ScanStatusEnum;
import vn.baodh.sparking.merchant.core.domain.model.ScanModel;
import vn.baodh.sparking.merchant.core.domain.repository.ParkingRepository;
import vn.baodh.sparking.merchant.core.domain.repository.UserRepository;
import vn.baodh.sparking.merchant.core.infra.jdbc.entity.ParkingEntity;

@Slf4j
@RestController
@RequestMapping("/mrc")
@RequiredArgsConstructor
public class DefaultHttpController {

  private final UserRepository userRepository;
  private final ParkingRepository parkingRepository;

  private String generateId(long key) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    return String.format("%4d%02d%014d",
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH) + 1, key);
  }

  public void handleSubmit(ScanModel messageModel) {
    try {
      if (messageModel.getStatus().equals(ScanStatusEnum.SUCCESS.getStatus())) {
        var phone = new String(Base64.getDecoder().decode(messageModel.getQrToken()));
        log.info(phone);
        var userId = userRepository.getUserIdByPhone(phone);
        log.info(userId);
        var parking = new ParkingEntity()
            .setParkingId(generateId(System.currentTimeMillis()))
            .setUserId(userId)
            .setLocationId(generateId(1))
            .setLicensePlate(messageModel.getLicensePlate());
        parkingRepository.create(parking);
      }
    } catch (Exception ignored) {
    }
  }

  @GetMapping("/ping")
  public ResponseEntity<?> ping(
      HttpServletRequest uri,
      @RequestParam Map<String, String> params
  ) {
    var phone = "0969189947";
    handleSubmit(new ScanModel()
        .setLicensePlate(params.get("license_plate"))
        .setQrToken(Base64.getEncoder().encodeToString(phone.getBytes()))
        .setStatus(ScanStatusEnum.SUCCESS.getStatus()));
    return ResponseEntity.ok("pong");
  }
}
