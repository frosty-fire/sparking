package vn.baodh.sparking.um.authorization.app.service.handler;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.um.authorization.app.service.FlowHandler;
import vn.baodh.sparking.um.authorization.domain.enumeration.StatusEnum;
import vn.baodh.sparking.um.authorization.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.um.authorization.domain.model.base.BaseResponse;
import vn.baodh.sparking.um.authorization.domain.model.TokenModel;
import vn.baodh.sparking.um.authorization.domain.model.UserModel;
import vn.baodh.sparking.um.authorization.domain.model.payload.LoginPayLoad;
import vn.baodh.sparking.um.authorization.infra.jdbc.master.JdbcUserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginHandler implements FlowHandler {

  private final JdbcUserRepository userRepository;
  // TODO
//  private final JwtTokenProvider tokenProvider;

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<TokenModel> response = new BaseResponse<>();
    try {
      LoginPayLoad payload = new LoginPayLoad().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        UserModel[] userModels = userRepository.getUserByPhone(payload.getPhone(), false)
            .toArray(new UserModel[0]);
        if (userModels.length == 0) {
          response.updateResponse(StatusEnum.USER_NOT_FOUND.getStatusCode());
        } else {
          UserModel user = userModels[0];
          if (!Objects.equals(user.getPin(), payload.getPin())) {
            response.updateResponse(StatusEnum.LOGIN_FAILED.getStatusCode());
          } else {
            response.data = new TokenModel[]{
                new TokenModel().setNeedOtp(true)
            };
            response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
          }
        }
      } else {
        response.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
      }
      log.info("[LoginHandler] Finish handle request: {}, response: {}",
          baseRequestInfo, response);
      return response;
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[LoginHandler] Handler handle >exception< with request: {}, response: {}, ",
          baseRequestInfo, response, exception);
      return response;
    }
  }
}
