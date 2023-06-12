package vn.baodh.sparking.parking.core.app.service.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.parking.core.domain.model.NotificationModel.ApiModel;
import vn.baodh.sparking.parking.core.domain.model.NotificationModel.ExtraInfo;
import vn.baodh.sparking.parking.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.base.BaseResponse;
import vn.baodh.sparking.parking.core.domain.model.payload.GiftMonthCardPayload;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.NotificationEntity;
import vn.baodh.sparking.parking.core.infra.jdbc.master.JdbcNotificationRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.master.JdbcUserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class GiftMonthCardHandler implements FlowHandler {

  private final JdbcUserRepository userRepository;
  private final JdbcNotificationRepository notificationRepository;

  private String generateId(long key) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    return String.format("%4d%02d%014d",
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH) + 1, key);
  }

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<?> response = new BaseResponse<>();
    try {
      GiftMonthCardPayload payload = new GiftMonthCardPayload().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        var userId = userRepository.getUserIdByPhone(payload.getPhone());
        var thisUser = userRepository.getUserByPhone(payload.getPhone());
        var notificationId = generateId(System.currentTimeMillis());
        var apiAccept = new ApiModel()
            .setApi("https://sparking.ngrok.app/prc/location/update-month-card?month_card_id="
                + payload.getMonthCardId() + "&phone=" + payload.getPhone() + "&location_id="
                + payload.getLocationId() + "&price=" + payload.getPrice()
                + "&use_user_id=" + payload.getUseUserId() + "&notification_id=" + notificationId
            )
            .setTitle("Chấp nhận");
        var apiReject = new ApiModel()
            .setApi("https://sparking.ngrok.app/prc/location/update-month-card?month_card_id="
                + payload.getMonthCardId() + "&phone=" + payload.getPhone() + "&location_id="
                + payload.getLocationId() + "&price=" + payload.getPrice()
                + "&use_user_id=" + userId + "&notification_id=" + notificationId)
            .setTitle("Từ chối");
        var notification = new NotificationEntity()
            .setNotificationId(notificationId)
            .setUserId(payload.getUseUserId())
            .setType("REQUEST")
            .setTitle(thisUser.get(0).getFullName() + " tặng bạn vé tháng")
            .setSubType("")
            .setExtraInfo(new ObjectMapper().writeValueAsString(
                new ExtraInfo()
                    .setHaveApi(true)
                    .setApiList(
                        List.of(new ApiModel[]{
                            apiAccept,
                            apiReject
                        })
                    )
            ));
        notificationRepository.create(notification);
        response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
      } else {
        response.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
      }
      log.info(
          "[UpdateMonthCardHandler] Finish handle with request: {}, response: {}, ",
          baseRequestInfo, response);
      return response;
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[UpdateMonthCardHandler] Handler handle >exception< with request: {}, response: {}, ",
          baseRequestInfo, response, exception);
      return response;
    }
  }
}
