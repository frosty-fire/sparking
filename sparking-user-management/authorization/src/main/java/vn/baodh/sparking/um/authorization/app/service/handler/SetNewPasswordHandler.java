package vn.baodh.sparking.um.authorization.app.service.handler;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.um.authorization.app.service.FlowHandler;
import vn.baodh.sparking.um.authorization.domain.enumeration.StatusEnum;
import vn.baodh.sparking.um.authorization.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.um.authorization.domain.model.base.BaseResponse;
import vn.baodh.sparking.um.authorization.domain.model.UserModel;
import vn.baodh.sparking.um.authorization.domain.model.payload.SetNewPasswordPayload;
import vn.baodh.sparking.um.authorization.domain.repository.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class SetNewPasswordHandler implements FlowHandler {

  private final UserRepository userRepository;

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<UserModel> response = new BaseResponse<>();
    try {
      SetNewPasswordPayload payload = new SetNewPasswordPayload().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        List<UserModel> users = userRepository.getUserByPhone(payload.getPhone(), false);
        if (Objects.equals(payload.getIsRequireOld(), "true")) {
          if (users.size() > 0 && Objects.equals(users.get(0).getPin(), payload.getOldPin())) {
            if (userRepository.update(
                new UserModel()
                    .setPhone(payload.getPhone())
                    .setPin(payload.getNewPin())
            )) {
              response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
            } else {
              response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
            }
          } else {
            response.updateResponse(StatusEnum.WRONG_INFO.getStatusCode());
          }
        } else {
          if (users.size() > 0) {
            if (userRepository.update(
                new UserModel()
                    .setPhone(payload.getPhone())
                    .setPin(payload.getNewPin())
            )) {
              response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
            } else {
              response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
            }
          } else {
            response.updateResponse(StatusEnum.WRONG_INFO.getStatusCode());
          }
        }
      } else {
        response.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
      }
      log.info("[SetNewPasswordHandler] Finish handle set new password requestPhone: {}, response: {}",
          baseRequestInfo.getParam("phone"), response);
      return response;
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error("[SetNewPasswordHandler] Handler handle >exception< with requestPhone: {}, response: {}, ",
          baseRequestInfo.getParam("phone"), response, exception);
      return response;
    }
  }
}
