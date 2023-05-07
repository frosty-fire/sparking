package vn.baodh.sparking.parking.core.app.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.parking.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.base.BaseResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultHandler implements FlowHandler {

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<String> response = new BaseResponse<>();
    response.updateResponse(StatusEnum.UNKNOWN.getStatusCode());
    log.info(
        "[DefaultHandler] Finish handle with request: {}, response: {}, ",
        baseRequestInfo, response);
    return response;
  }
}
