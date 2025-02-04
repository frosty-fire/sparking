package vn.baodh.sparking.parking.core.app.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.parking.core.domain.model.VehicleModel;
import vn.baodh.sparking.parking.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.base.BaseResponse;
import vn.baodh.sparking.parking.core.domain.model.payload.GetVehiclesPayload;
import vn.baodh.sparking.parking.core.domain.repository.ParkingRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetVehiclesHandler implements FlowHandler {

  private final ParkingRepository parkingRepository;

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<VehicleModel> response = new BaseResponse<>();
    try {
      GetVehiclesPayload payload = new GetVehiclesPayload().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        response.setData(parkingRepository.getVehiclesByPhone(payload.getPhone())
            .toArray(new VehicleModel[0]));
        response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
      } else {
        response.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
      }
      log.info(
          "[GetVehicleHandler] Finish handle with request: {}, response: {}, ",
          baseRequestInfo, response);
      return response;
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[GetVehicleHandler] Handler handle >exception< with request: {}, response: {}, ",
          baseRequestInfo, response, exception);
      return response;
    }
  }
}
