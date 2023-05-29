package vn.baodh.sparking.parking.core.app.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.parking.core.domain.model.NotificationModel;
import vn.baodh.sparking.parking.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.base.BaseResponse;
import vn.baodh.sparking.parking.core.domain.model.payload.GetNotificationPayLoad;
import vn.baodh.sparking.parking.core.domain.repository.NotificationRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetNotificationsHandler implements FlowHandler {

  private final NotificationRepository notificationRepository;

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<NotificationModel> response = new BaseResponse<>();
    try {
      GetNotificationPayLoad payload = new GetNotificationPayLoad().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        response.setData(notificationRepository.getNotificationByPhone(payload.getPhone())
            .toArray(new NotificationModel[0]));
        response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
      } else {
        response.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
      }
      log.info(
          "[GetNotificationsHandler] Finish handle with request: {}, response: {}, ",
          baseRequestInfo, response);
      return response;
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[GetNotificationsHandler] Handler handle >exception< with request: {}, response: {}, ",
          baseRequestInfo, response, exception);
      return response;
    }
  }
}
