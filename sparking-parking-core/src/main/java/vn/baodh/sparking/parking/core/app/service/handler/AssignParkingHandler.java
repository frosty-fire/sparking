package vn.baodh.sparking.parking.core.app.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.parking.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.base.BaseResponse;
import vn.baodh.sparking.parking.core.domain.model.payload.AssignParkingPayload;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.ParkingEntity;
import vn.baodh.sparking.parking.core.infra.jdbc.master.JdbcNotificationRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.master.JdbcParkingRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.master.JdbcUserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class AssignParkingHandler implements FlowHandler {

  private final JdbcUserRepository userRepository;
  private final JdbcNotificationRepository notificationRepository;
  private final JdbcParkingRepository parkingRepository;

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<?> response = new BaseResponse<>();
    try {
      AssignParkingPayload payload = new AssignParkingPayload().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        parkingRepository.updateAssign(new ParkingEntity()
            .setParkingId(payload.getParkingId())
            .setUserId(payload.getUserId())
        );
        response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
      } else {
        response.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
      }
      log.info(
          "[AssignParkingHandler] Finish handle with request: {}, response: {}, ",
          baseRequestInfo, response);
      return response;
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[AssignParkingHandler] Handler handle >exception< with request: {}, response: {}, ",
          baseRequestInfo, response, exception);
      return response;
    }
  }
}
