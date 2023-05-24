package vn.baodh.sparking.merchant.core.infra.controller.http;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import vn.baodh.sparking.merchant.core.app.cache.CacheManagement;
import vn.baodh.sparking.merchant.core.domain.enumeration.ScanStatusEnum;
import vn.baodh.sparking.merchant.core.domain.model.ScanModel;
import vn.baodh.sparking.merchant.core.domain.repository.ParkingRepository;
import vn.baodh.sparking.merchant.core.domain.repository.UserRepository;
import vn.baodh.sparking.merchant.core.infra.jdbc.entity.ParkingEntity;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ScanWebSocketController {

  private final CacheManagement cache;
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

  @MessageMapping("license-plate")
  @SendTo("/room/mock")
  public ScanModel licensePlate(ScanModel scanModel) {
    log.info(String.valueOf(scanModel));
    ScanModel message = new ScanModel();
    if (Objects.equals(scanModel.getStatus(), ScanStatusEnum.SUBMIT.getStatus())) {
      message.setQrToken(scanModel.getQrToken());
      message.setLicensePlate(scanModel.getLicensePlate());
      message.setStatus(ScanStatusEnum.SUCCESS.getStatus());
      handleSubmit(message);
      cache.deleteQrTokenSession(scanModel.getQrToken());
    } else if (Objects.equals(scanModel.getStatus(), ScanStatusEnum.QR_ERROR.getStatus())) {
      message.setStatus(ScanStatusEnum.QR_FAILED.getStatus());
      cache.deleteQrTokenSession(scanModel.getQrToken());
    } else if (Objects.equals(scanModel.getStatus(), ScanStatusEnum.LICENSE_ERROR.getStatus())) {
      if (cache.getQrTokenSession(scanModel.getQrToken()) >= 3) {
        message.setStatus(ScanStatusEnum.FAILED.getStatus());
        cache.deleteQrTokenSession(scanModel.getQrToken());
      } else {
        message.setQrToken(scanModel.getQrToken());
        message.setStatus(ScanStatusEnum.LICENSE_FAILED.getStatus());
      }
    } else {
      message.setStatus(ScanStatusEnum.FAILED.getStatus());
      cache.deleteQrTokenSession(scanModel.getQrToken());
    }
    return message;
  }

  @MessageMapping("qr-scan")
  @SendTo("/room/mock")
  public ScanModel qrToken(ScanModel scanModel) {
    log.info(String.valueOf(scanModel));
    ScanModel message = new ScanModel();
    message.setQrToken(scanModel.getQrToken());
    message.setStatus(ScanStatusEnum.NEED_LICENSE_PLATE.getStatus());
    cache.initQrTokenSession(scanModel.getQrToken());
    return message;
  }
}
