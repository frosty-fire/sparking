package vn.baodh.sparking.parking.core.app.service.handler;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.parking.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.base.BaseResponse;
import vn.baodh.sparking.parking.core.domain.model.TokenModel;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckOutHandler implements FlowHandler {

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<TokenModel> response = new BaseResponse<>();
    try {
      String token = "sparking-check-out-private-key" + new Date();
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 60);
      response.data = new TokenModel[]{
          new TokenModel()
              .setQrToken(Base64.getEncoder().encodeToString(token.getBytes()))
              .setExpiredTime(String.valueOf(calendar.getTimeInMillis()))
      };
      response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
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
