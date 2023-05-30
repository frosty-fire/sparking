package vn.baodh.sparking.parking.core.app.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.app.service.handler.CheckInHandler;
import vn.baodh.sparking.parking.core.app.service.handler.CheckOutHandler;
import vn.baodh.sparking.parking.core.app.service.handler.DefaultHandler;
import vn.baodh.sparking.parking.core.app.service.handler.GetLocationDetailHandler;
import vn.baodh.sparking.parking.core.app.service.handler.GetLocationsHandler;
import vn.baodh.sparking.parking.core.app.service.handler.GetNewVoucherHandler;
import vn.baodh.sparking.parking.core.app.service.handler.GetNotificationsHandler;
import vn.baodh.sparking.parking.core.app.service.handler.GetVehicleHandler;
import vn.baodh.sparking.parking.core.app.service.handler.GetVehiclesHandler;
import vn.baodh.sparking.parking.core.app.service.handler.SubmitQrHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.FlowEnum;

@Component
@RequiredArgsConstructor
public class FlowMapping {

  // default
  private final DefaultHandler defaultHandler;

  // qr
  private final CheckInHandler checkInHandler;
  private final CheckOutHandler checkOutHandler;

  // parking
  private final GetVehicleHandler getVehicleHandler;
  private final GetVehiclesHandler getVehiclesHandler;

  // main
  private final SubmitQrHandler submitQrHandler;

  // voucher
  private final GetNewVoucherHandler getNewVoucherHandler;

  // notification
  private final GetNotificationsHandler getNotificationsHandler;

  // location
  private final GetLocationsHandler getLocationsHandler;
  private final GetLocationDetailHandler getLocationDetailHandler;

  // month card

  public FlowHandler getFlowHandler(FlowEnum flowEnum) {
    return switch (flowEnum) {
      case CHECK_IN -> checkInHandler;
      case CHECK_OUT -> checkOutHandler;
      case GET_VEHICLE -> getVehicleHandler;
      case GET_VEHICLES -> getVehiclesHandler;
      case GET_VOUCHERS -> getNewVoucherHandler;
      case SUBMIT_QR -> submitQrHandler;
      case GET_NOTIFICATIONS -> getNotificationsHandler;
      case GET_LOCATIONS -> getLocationsHandler;
      case GET_LOCATION -> getLocationDetailHandler;
      case SUBMIT_MONTH_CARD -> defaultHandler;
      case UPDATE_MONTH_CARD -> defaultHandler;
      case GET_MONTH_CARD -> defaultHandler;
      default -> defaultHandler;
    };
  }
}
