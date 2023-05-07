package vn.baodh.sparking.um.authorization.app.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.um.authorization.app.service.FlowHandler;
import vn.baodh.sparking.um.authorization.domain.enumeration.StatusEnum;
import vn.baodh.sparking.um.authorization.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.um.authorization.domain.model.base.BaseResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultHandler implements FlowHandler {

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<String> response = new BaseResponse<>();
    response.updateResponse(StatusEnum.UNKNOWN.getStatusCode());
    log.info("[DefaultHandler] Finish handle request: {}, response: {}",
        baseRequestInfo, response);
    return response;
  }
}
