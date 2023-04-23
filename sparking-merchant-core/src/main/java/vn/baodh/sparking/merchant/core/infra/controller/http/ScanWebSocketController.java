package vn.baodh.sparking.merchant.core.infra.controller.http;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import vn.baodh.sparking.merchant.core.app.cache.CacheManagement;
import vn.baodh.sparking.merchant.core.app.cache.RedisCache;
import vn.baodh.sparking.merchant.core.domain.enumeration.ScanStatusEnum;
import vn.baodh.sparking.merchant.core.domain.model.ScanModel;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ScanWebSocketController {

    private final CacheManagement cache;

//  @SubscribeMapping("/room/{room_id}")
//  @SendTo("/room/{room_id}")
//  public String subscribeRoom(@DestinationVariable String room_id) {
//    if (room_id.matches("^\\d$")) {
//      return String.format("Tham gia phòng %s thành công", room_id);
//    } else {
//      return "Số phòng phải là một số nguyên";
//    }
//  }

    @MessageMapping("license-plate")
    @SendTo("/room/mock")
    public ScanModel licensePlate(ScanModel scanModel) {
        ScanModel message = new ScanModel();
        if (Objects.equals(scanModel.getStatus(), ScanStatusEnum.SUBMIT.getStatus())) {
            message.setQrToken(scanModel.getQrToken());
            message.setLicensePlate(scanModel.getLicensePlate());
            message.setStatus(ScanStatusEnum.SUCCESS.getStatus());
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
        ScanModel message = new ScanModel();
        message.setQrToken(scanModel.getQrToken());
        message.setStatus(ScanStatusEnum.NEED_LICENSE_PLATE.getStatus());
        cache.initQrTokenSession(scanModel.getQrToken());
        return message;
    }
}
