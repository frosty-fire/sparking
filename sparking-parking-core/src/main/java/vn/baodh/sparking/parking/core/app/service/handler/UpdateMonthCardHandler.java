package vn.baodh.sparking.parking.core.app.service.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.parking.core.domain.model.MonthCardModel;
import vn.baodh.sparking.parking.core.domain.model.NotificationModel.ExtraInfo;
import vn.baodh.sparking.parking.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.base.BaseResponse;
import vn.baodh.sparking.parking.core.domain.model.payload.UpdateMonthCardPayload;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.NotificationEntity;
import vn.baodh.sparking.parking.core.infra.jdbc.master.JdbcMonthCardRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.master.JdbcNotificationRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.master.JdbcUserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateMonthCardHandler implements FlowHandler {

  private final JdbcUserRepository userRepository;
  private final JdbcMonthCardRepository monthCardRepository;
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
      UpdateMonthCardPayload payload = new UpdateMonthCardPayload().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        var userId = userRepository.getUserIdByPhone(payload.getPhone());
        var thatUser = userRepository.getUserById(payload.getUseUserId(), true);
        var monthTicket = new MonthCardModel()
            .setMonthCardId(payload.getMonthCardId())
            .setLocationId(payload.getLocationId())
            .setUseUserId((payload.getUseUserId() != null && !payload.getUseUserId().equals(""))
                ? payload.getUseUserId() : userId)
            .setSourceUserId(userId)
            .setPrice(payload.getPrice())
            .setNumber(
                (payload.getAdditionNumber() != null && !payload.getAdditionNumber().equals(""))
                    ? payload.getAdditionNumber() : "0"
            );
        monthCardRepository.update(monthTicket.toEntity());
        if (payload.getNotificationId() != null && !payload.getNotificationId().equals("")) {
          notificationRepository.delete(payload.getNotificationId());
          var statusMessage = (Objects.equals(payload.getUseUserId(), userId))
              ? "Vé tháng bạn tặng không được nhận" : thatUser.get(0).getFullName() + " đã nhận vé tháng";
          var notification = new NotificationEntity()
              .setNotificationId(generateId(System.currentTimeMillis()))
              .setUserId(userId)
              .setType("MONTH_TICKET")
              .setTitle("Tặng vé tháng")
              .setSubType("")
              .setExtraInfo(new ObjectMapper().writeValueAsString(
                  new ExtraInfo()
                      .setHaveApi(false)
                      .setDescription(statusMessage)
              ));
          notificationRepository.create(notification);
        }
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
