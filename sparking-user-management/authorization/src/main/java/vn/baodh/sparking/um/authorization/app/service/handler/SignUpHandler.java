package vn.baodh.sparking.um.authorization.app.service.handler;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.um.authorization.app.service.FlowHandler;
import vn.baodh.sparking.um.authorization.domain.enumeration.StatusEnum;
import vn.baodh.sparking.um.authorization.domain.model.BaseRequestInfo;
import vn.baodh.sparking.um.authorization.domain.model.BaseResponse;
import vn.baodh.sparking.um.authorization.domain.model.UserModel;
import vn.baodh.sparking.um.authorization.domain.model.payload.SignUpPayload;
import vn.baodh.sparking.um.authorization.infra.jdbc.master.JdbcUserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class SignUpHandler implements FlowHandler {

  private final JdbcUserRepository userRepository;

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<UserModel> response = new BaseResponse<>();
    try {
      SignUpPayload payload = new SignUpPayload().getPayLoadInfo(baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        UserModel userModel = preProcessUserInfo(payload);
        userRepository.create(userModel);
        response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
      } else {
        response.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
      }
      log.info("[SignUpHandler] Finish handle signup requestPhone: {}, response: {}",
          baseRequestInfo.getParam("phone"), response);
      return response;
    } catch (DuplicateKeyException exception) {
      response.updateResponse(StatusEnum.DUPLICATE_PHONE.getStatusCode());
      log.info("[SignUpHandler] Handler handle >duplicate key< with requestPhone: {}, response: {}",
          baseRequestInfo.getParam("phone"), response);
      return response;
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error("[SignUpHandler] Handler handle >exception< with requestPhone: {}, response: {}, ",
          baseRequestInfo.getParam("phone"), response, exception);
      return response;
    }
  }

  private UserModel preProcessUserInfo(SignUpPayload payload) {
    return new UserModel()
        .setUserId(generateUserId(Long.parseLong(payload.getPhone())))
        .setPhone(payload.getPhone())
        .setPin(payload.getPin()) // TODO: hash_pin
        .setFullName(payload.getFullName())
        .setGender(payload.getGender())
        .setEmail(payload.getEmail())
        .setBirthday(payload.getBirthday())
        .setImageUrl(payload.getImageUrl());
  }

  private String generateUserId(long key) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    return String.format("%4d%02d%014d",
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH) + 1, key);
  }
}
