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
import vn.baodh.sparking.parking.core.domain.model.payload.SubmitMonthCardPayload;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.MonthCardEntity;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.NotificationEntity;
import vn.baodh.sparking.parking.core.infra.jdbc.master.JdbcMonthCardRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.master.JdbcNotificationRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.master.JdbcUserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubmitMonthCardHandler implements FlowHandler {

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
      SubmitMonthCardPayload payload = new SubmitMonthCardPayload().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        var userId = userRepository.getUserIdByPhone(payload.getPhone());
        var monthCards = monthCardRepository.getMonthCardByPhone(payload.getPhone());
        MonthCardEntity validTicket = null;
        for (var monthCard : monthCards) {
          if (Objects.equals(monthCard.getLocationId(), payload.getLocationId())) {
            validTicket = monthCard;
            break;
          }
        }
        if (validTicket != null) {
          var monthTicket = new MonthCardModel()
              .setMonthCardId(validTicket.getMonthCardId())
              .setLocationId(validTicket.getLocationId())
              .setUseUserId(validTicket.getUseUserId())
              .setSourceUserId(validTicket.getSourceUserId())
              .setPrice(validTicket.getPrice())
              .setNumber(payload.getNumber());
          monthCardRepository.update(monthTicket.toEntity());
        } else {
          var monthTicket = new MonthCardModel()
              .setMonthCardId(generateId(System.currentTimeMillis()))
              .setLocationId(payload.getLocationId())
              .setUseUserId(userId)
              .setSourceUserId(userId)
              .setPrice(payload.getPrice())
              .setNumber(payload.getNumber());
          monthCardRepository.create(monthTicket.toEntity());
        }
        var notification = new NotificationEntity()
            .setNotificationId(generateId(System.currentTimeMillis()))
            .setUserId(userId)
            .setType("MONTH_TICKET")
            .setTitle("Đăng ký vé tháng thành công")
            .setSubType("")
            .setExtraInfo(new ObjectMapper().writeValueAsString(
                new ExtraInfo()
                    .setHaveApi(false)
                    .setDescription(payload.getNumber() + " tháng - " + payload.getLocationId())
            ));
        notificationRepository.create(notification);
        response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
      } else {
        response.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
      }
      log.info(
          "[SubmitMonthCardHandler] Finish handle with request: {}, response: {}, ",
          baseRequestInfo, response);
      return response;
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[SubmitMonthCardHandler] Handler handle >exception< with request: {}, response: {}, ",
          baseRequestInfo, response, exception);
      return response;
    }
  }
}
