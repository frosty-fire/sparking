package vn.baodh.sparking.um.authorization.app.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.um.authorization.app.service.FlowHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.CheckPhoneHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.DefaultHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.GetUserHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.LoginHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.SetNewPasswordHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.SignUpHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.VerifyPhoneHandler;
import vn.baodh.sparking.um.authorization.domain.enumeration.FlowEnum;

@Component
@RequiredArgsConstructor
public class FlowMapping {

  // default
  private final DefaultHandler defaultHandler;

  // auth
  private final CheckPhoneHandler checkPhoneHandler;
  private final VerifyPhoneHandler verifyPhoneHandler;
  private final LoginHandler loginHandler;
  private final SignUpHandler signUpHandler;

  // profile
  private final GetUserHandler getUserHandler;
  private final SetNewPasswordHandler setNewPasswordHandler;
  // TODO
//  private final GetFriends getFriends;

  public FlowHandler getFlowHandler(FlowEnum flowEnum) {
    return switch (flowEnum) {
      case CHECK_PHONE -> checkPhoneHandler;
      case VERIFY_PHONE -> verifyPhoneHandler;
      case LOGIN -> loginHandler;
      case SIGN_UP -> signUpHandler;
      case GET_USER -> getUserHandler;
      case SET_NEW_PASSWORD -> setNewPasswordHandler;
//      case GET_FIRIENDS -> getFriends
      default -> defaultHandler;
    };
  }
}
