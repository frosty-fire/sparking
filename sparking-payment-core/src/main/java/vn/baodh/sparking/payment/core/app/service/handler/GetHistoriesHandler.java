package vn.baodh.sparking.payment.core.app.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.payment.core.app.service.FlowHandler;
import vn.baodh.sparking.payment.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.payment.core.domain.model.HistoryModel;
import vn.baodh.sparking.payment.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.payment.core.domain.model.base.BaseResponse;
import vn.baodh.sparking.payment.core.domain.model.payload.GetHistoriesPayload;
import vn.baodh.sparking.payment.core.infra.jdbc.master.JdbcPaymentRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetHistoriesHandler implements FlowHandler {

  private final JdbcPaymentRepository paymentRepository;

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<HistoryModel> response = new BaseResponse<>();
    try {
      GetHistoriesPayload payload = new GetHistoriesPayload().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        response.data = paymentRepository.getHistoriesByPhoneAndType(
                payload.getPhone(),
                payload.getType(),
                payload.getPrevId())
            .toArray(new HistoryModel[0]);
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
