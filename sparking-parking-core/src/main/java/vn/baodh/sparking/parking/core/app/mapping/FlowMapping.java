package vn.baodh.sparking.parking.core.app.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.app.service.handler.CheckInHandler;
import vn.baodh.sparking.parking.core.app.service.handler.CheckOutHandler;
import vn.baodh.sparking.parking.core.app.service.handler.DefaultHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.FlowEnum;

@Component
@RequiredArgsConstructor
public class FlowMapping {

  // default
  private final DefaultHandler defaultHandler;

  // auth
  private final CheckInHandler checkInHandler;
  private final CheckOutHandler checkOutHandler;

  public FlowHandler getFlowHandler(FlowEnum flowEnum) {
    return switch (flowEnum) {
      case CHECK_IN -> checkInHandler;
      case CHECK_OUT -> checkOutHandler;
      default -> defaultHandler;
    };
  }
}
