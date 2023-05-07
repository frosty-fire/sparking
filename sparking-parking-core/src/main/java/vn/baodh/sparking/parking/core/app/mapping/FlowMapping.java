package vn.baodh.sparking.parking.core.app.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.app.service.handler.CheckInHandler;
import vn.baodh.sparking.parking.core.app.service.handler.CheckOutHandler;
import vn.baodh.sparking.parking.core.app.service.handler.DefaultHandler;
import vn.baodh.sparking.parking.core.app.service.handler.GetVehicleHandler;
import vn.baodh.sparking.parking.core.app.service.handler.GetVehiclesHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.FlowEnum;

@Component
@RequiredArgsConstructor
public class FlowMapping {

  // default
  private final DefaultHandler defaultHandler;

  // auth
  private final CheckInHandler checkInHandler;
  private final CheckOutHandler checkOutHandler;

  // parking
  private final GetVehicleHandler getVehicleHandler;
  private final GetVehiclesHandler getVehiclesHandler;

  public FlowHandler getFlowHandler(FlowEnum flowEnum) {
    return switch (flowEnum) {
      case CHECK_IN -> checkInHandler;
      case CHECK_OUT -> checkOutHandler;
      case GET_VEHICLE -> getVehicleHandler;
      case GET_VEHICLES -> getVehiclesHandler;
      default -> defaultHandler;
    };
  }
}
