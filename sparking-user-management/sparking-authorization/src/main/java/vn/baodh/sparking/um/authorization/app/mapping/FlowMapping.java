package vn.baodh.sparking.um.authorization.app.mapping;

import com.mysql.cj.x.protobuf.MysqlxCrud.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.um.authorization.app.service.FlowHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.CheckPhoneHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.DefaultHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.GetFriendsHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.GetUnknownUsersHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.GetUserHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.LoginHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.SendFriendRequestHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.SetNewPasswordHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.SignUpHandler;
import vn.baodh.sparking.um.authorization.app.service.handler.UpdateFriendRequestHandler;
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

  // friend
  private final GetUnknownUsersHandler getUnknownUsersHandler;
  private final GetFriendsHandler getFriendsHandler;
  private final SendFriendRequestHandler sendFriendRequestHandler;
  private final UpdateFriendRequestHandler updateFriendRequestHandler;

  public FlowHandler getFlowHandler(FlowEnum flowEnum) {
    return switch (flowEnum) {
      case CHECK_PHONE -> checkPhoneHandler;
      case VERIFY_PHONE -> verifyPhoneHandler;
      case LOGIN -> loginHandler;
      case SIGN_UP -> signUpHandler;
      case GET_USER -> getUserHandler;
      case SET_NEW_PASSWORD -> setNewPasswordHandler;
      case GET_UNKNOWN_USER -> getUnknownUsersHandler;
      case GET_FRIENDS -> getFriendsHandler;
      case SEND_FRIEND_REQUEST -> sendFriendRequestHandler;
      case UPDATE_FRIEND_REQUEST -> updateFriendRequestHandler;
      default -> defaultHandler;
    };
  }
}
