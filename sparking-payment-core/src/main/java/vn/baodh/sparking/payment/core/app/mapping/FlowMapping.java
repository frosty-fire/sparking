package vn.baodh.sparking.payment.core.app.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.payment.core.app.service.FlowHandler;
import vn.baodh.sparking.payment.core.app.service.handler.DefaultHandler;
import vn.baodh.sparking.payment.core.app.service.handler.GetHistoriesHandler;
import vn.baodh.sparking.payment.core.domain.enumeration.FlowEnum;

@Component
@RequiredArgsConstructor
public class FlowMapping {

  // default
  private final DefaultHandler defaultHandler;

  // history
  private final GetHistoriesHandler getHistoriesHandler;

  public FlowHandler getFlowHandler(FlowEnum flowEnum) {
    return switch (flowEnum) {
      case GET_HISTORIES -> getHistoriesHandler;
      default -> defaultHandler;
    };
  }
}
