package vn.baodh.sparking.parking.core.app.service.handler;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.security.qrtoken.RSA;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.parking.core.domain.model.QrType;
import vn.baodh.sparking.parking.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.base.BaseResponse;
import vn.baodh.sparking.parking.core.domain.model.payload.QrSubmitPayload;
import vn.baodh.sparking.parking.core.domain.repository.ParkingRepository;
import vn.baodh.sparking.parking.core.domain.repository.UserRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.ParkingEntity;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubmitQrHandler implements FlowHandler {

  private final RSA rsa;
  private final UserRepository userRepository;
  private final ParkingRepository parkingRepository;

  private String generateId(long key) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    return String.format("%4d%02d%014d",
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH) + 1, key);
  }

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<String> response = new BaseResponse<>();
    try {
      QrSubmitPayload payload = new QrSubmitPayload().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        var qrToken = payload.getQrToken();
        var qrModel = rsa.decryptQr(qrToken);
        log.info("{}", qrModel);
        var userId = userRepository.getUserIdByPhone(qrModel.getUserPhone());
        if (qrModel.getType() == QrType.QR_CHECK_OUT) {
          var vehicles = parkingRepository.getVehicleById(qrModel.getVehicleId());
          if (vehicles.size() > 0) {
            var vehicle = vehicles.get(0);
            log.info("{}", vehicle);
            if (Objects.equals(vehicle.getLicensePlate(), payload.getLicensePlate())) {
              var parking = new ParkingEntity()
                  .setParkingId(qrModel.getVehicleId())
                  .setStatus("exit")
                  .setFee(String.valueOf(vehicle.getFee()));
              parkingRepository.updateExit(parking);
            } else {
              response.updateResponse(StatusEnum.LICENSE_NOT_MATCH.getStatusCode());
            }
          } else {
            response.updateResponse(StatusEnum.VEHICLE_NOT_FOUND.getStatusCode());
          }
        } else {
          var parking = new ParkingEntity()
              .setParkingId(generateId(System.currentTimeMillis()))
              .setUserId(userId)
              .setLocationId("20230500000000000001") // TODO: update new location_id
              .setLicensePlate(payload.getLicensePlate());
          parkingRepository.create(parking);
        }
        response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
      } else {
        response.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
      }
      log.info(
          "[SubmitQrHandler] Finish handle with request: {}, response: {}, ",
          baseRequestInfo, response);
      return response;
    } catch (DuplicateKeyException exception) {
      response.updateResponse(StatusEnum.VEHICLE_EXISTED.getStatusCode());
      log.error(
          "[SubmitQrHandler] Handler handle >exception< with request: {}, response: {}, ",
          baseRequestInfo, response, exception);
      return response;
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[SubmitQrHandler] Handler handle >exception< with request: {}, response: {}, ",
          baseRequestInfo, response, exception);
      return response;
    }
  }
}
