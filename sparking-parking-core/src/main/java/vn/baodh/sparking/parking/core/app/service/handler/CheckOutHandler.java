package vn.baodh.sparking.parking.core.app.service.handler;

import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.security.qrtoken.RSA;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.parking.core.domain.model.QrModel;
import vn.baodh.sparking.parking.core.domain.model.QrType;
import vn.baodh.sparking.parking.core.domain.model.TokenModel;
import vn.baodh.sparking.parking.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.base.BaseResponse;
import vn.baodh.sparking.parking.core.domain.model.payload.CheckOutPayload;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckOutHandler implements FlowHandler {

  private final RSA rsa;

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<TokenModel> response = new BaseResponse<>();
    try {
      CheckOutPayload payload = new CheckOutPayload().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        var timestamp = System.currentTimeMillis();
        var calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 60);
        response.setData(new TokenModel[]{
            new TokenModel()
                .setQrToken(rsa.encryptQr(new QrModel()
                    .setSocketKey(payload.getSocketKey())
                    .setType(QrType.QR_CHECK_OUT)
                    .setTimestamp(timestamp)
                    .setUserPhone(payload.getPhone())
                    .setVehicleId(payload.getVehicleId())
                    .setVoucherId(payload.getVoucherId())
                ))
                .setExpiredTime(String.valueOf(calendar.getTimeInMillis()))
        });
        response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
      } else {
        response.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
      }
      log.info(
          "[CheckOutHandler] Finish handle with request: {}, response: {}, ",
          baseRequestInfo, response);
      return response;
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[CheckOutHandler] Handler handle >exception< with request: {}, response: {}, ",
          baseRequestInfo, response, exception);
      return response;
    }
  }
}
