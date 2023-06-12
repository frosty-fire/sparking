package vn.baodh.sparking.parking.core.app.service.handler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.parking.core.domain.model.LocationDetailModel;
import vn.baodh.sparking.parking.core.domain.model.MonthCardDetailModel;
import vn.baodh.sparking.parking.core.domain.model.MonthCardModel;
import vn.baodh.sparking.parking.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.base.BaseResponse;
import vn.baodh.sparking.parking.core.domain.model.payload.GetMonthCardPayload;
import vn.baodh.sparking.parking.core.domain.model.payload.SubmitMonthCardPayload;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.MonthCardEntity;
import vn.baodh.sparking.parking.core.infra.jdbc.master.JdbcLocationRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.master.JdbcMonthCardRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.master.JdbcUserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetMonthCardHandler implements FlowHandler {

  private final JdbcMonthCardRepository monthCardRepository;
  private final JdbcLocationRepository locationRepository;

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<MonthCardDetailModel> response = new BaseResponse<>();
    try {
      GetMonthCardPayload payload = new GetMonthCardPayload().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        var data = new ArrayList<MonthCardDetailModel>();
        var monthCards = monthCardRepository.getMonthCardByPhone(payload.getPhone());
        for (var monthCard : monthCards) {
          var locations = locationRepository.getLocationById(monthCard.getLocationId());
          var location = locations.size() > 0 ? locations.get(0) : new LocationDetailModel();
          var item = new MonthCardDetailModel()
              .setMonthCardId(monthCard.getMonthCardId())
              .setLocationId(monthCard.getLocationId())
              .setLocationName(location.getLocationName())
              .setLocationAddress(location.getAddress())
              .setLocationImageUrl(location.getImageUrl())
              .setUseUserId(monthCard.getUseUserId())
              .setSourceUserId(monthCard.getSourceUserId())
              .setPrice(monthCard.getSourceUserId())
              .setNumber(monthCard.getNumber())
              .setStartDate(monthCard.getCreatedAt());
          data.add(item);
        }
        response.setData(data.toArray(new MonthCardDetailModel[0]));
        response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
      } else {
        response.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
      }
      log.info(
          "[GetMonthCardHandler] Finish handle with request: {}, response: {}, ",
          baseRequestInfo, response);
      return response;
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[GetMonthCardHandler] Handler handle >exception< with request: {}, response: {}, ",
          baseRequestInfo, response, exception);
      return response;
    }
  }
}
