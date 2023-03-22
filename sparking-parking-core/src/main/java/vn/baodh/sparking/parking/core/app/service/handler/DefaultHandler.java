package vn.baodh.sparking.parking.core.app.service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.parking.core.domain.model.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.BaseResponse;

@Component
@RequiredArgsConstructor
public class DefaultHandler implements FlowHandler {

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<String> response = new BaseResponse<>();
    response.updateResponse(StatusEnum.UNKNOWN.getStatusCode());
    return response;
  }
}
