package vn.baodh.sparking.payment.core.app.service.handler;

import java.util.Calendar;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.payment.core.app.service.FlowHandler;
import vn.baodh.sparking.payment.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.payment.core.domain.model.HistoryModel;
import vn.baodh.sparking.payment.core.domain.model.HistoryModel.ExtraInfo;
import vn.baodh.sparking.payment.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.payment.core.domain.model.base.BaseResponse;
import vn.baodh.sparking.payment.core.domain.model.payload.GetHistoriesPayload;
import vn.baodh.sparking.payment.core.domain.model.payload.PayCheckOutPayload;
import vn.baodh.sparking.payment.core.domain.model.payload.PayMonthCardPayload;
import vn.baodh.sparking.payment.core.infra.jdbc.master.JdbcLocationRepository;
import vn.baodh.sparking.payment.core.infra.jdbc.master.JdbcPaymentRepository;
import vn.baodh.sparking.payment.core.infra.jdbc.master.JdbcUserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayMonthCardHandler implements FlowHandler {

  private final JdbcPaymentRepository paymentRepository;
  private final JdbcLocationRepository locationRepository;
  private final JdbcUserRepository userRepository;

  private String generateId(long key) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    return String.format("%4d%02d%014d",
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH) + 1, key);
  }

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<HistoryModel> response = new BaseResponse<>();
    try {
      PayMonthCardPayload payload = new PayMonthCardPayload().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        var userId = userRepository.getUserIdByPhone(payload.getPhone());
        var locations = locationRepository.getLocationById(payload.getLocationId());
        var history = new HistoryModel()
            .setHistoryId(generateId(System.currentTimeMillis()))
            .setUserId(userId)
            .setType("month-ticket")
            .setTitle("Mua vé tháng")
            .setExtraInfo(new ExtraInfo()
                .setDescription(locations.get(0).getLocationName())
                .setPrice(payload.getPrice())
            );
        paymentRepository.create(history);
        response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
      } else {
        response.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
      }
      log.info("[GetHistoriesHandler] Finish handle request: {}, response: {}",
          baseRequestInfo, response);
      return response;
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[GetHistoriesHandler] Handler handle >exception< with request: {}, response: {}, ",
          baseRequestInfo, response, exception);
      return response;
    }
  }
}
