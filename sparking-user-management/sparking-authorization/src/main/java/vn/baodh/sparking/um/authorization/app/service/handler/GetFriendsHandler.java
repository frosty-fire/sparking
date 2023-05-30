package vn.baodh.sparking.um.authorization.app.service.handler;

import java.util.ArrayList;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.um.authorization.app.service.FlowHandler;
import vn.baodh.sparking.um.authorization.domain.enumeration.StatusEnum;
import vn.baodh.sparking.um.authorization.domain.model.FriendModel;
import vn.baodh.sparking.um.authorization.domain.model.UserModel;
import vn.baodh.sparking.um.authorization.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.um.authorization.domain.model.base.BaseResponse;
import vn.baodh.sparking.um.authorization.domain.model.payload.GetFriendsPayload;
import vn.baodh.sparking.um.authorization.domain.model.payload.GetUnknownUserPayload;
import vn.baodh.sparking.um.authorization.domain.model.payload.GetUserPayload;
import vn.baodh.sparking.um.authorization.infra.jdbc.master.JdbcFriendRepository;
import vn.baodh.sparking.um.authorization.infra.jdbc.master.JdbcUserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetFriendsHandler implements FlowHandler {

  private final JdbcUserRepository userRepository;
  private final JdbcFriendRepository friendRepository;

  @Override
  public BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo) {
    BaseResponse<FriendModel> response = new BaseResponse<>();
    try {
      GetFriendsPayload payload = new GetFriendsPayload().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        var userId = userRepository.getUserIdByPhone(payload.getThisPhone());
        var friendListId = friendRepository.getFriends(userId);
        var friends = new ArrayList<FriendModel>();
        if (payload.getPhoneSearching() != null && !payload.getPhoneSearching().equals("")) {
          var likePhoneUser = userRepository.getUnknownLikePhone(payload.getPhoneSearching(), true)
              .toArray(new FriendModel[0]);
          for (var item : likePhoneUser) {
            log.info("{}", item);
            if (friendListId.contains(item.getUserId()) && !Objects.equals(item.getUserId(),
                userId)) {
              friends.add(item);
            }
          }
        } else {
          for (var id: friendListId) {
            var user = userRepository.getFriendById(id, true);
            if (user != null && user.size() > 0) {
              friends.add(user.get(0));
            }
          }
        }
        response.data = friends.toArray(new FriendModel[0]);
        response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
      } else {
        response.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
      }
      log.info("[GetFriendsHandler] Finish handle request: {}, response: {}",
          baseRequestInfo, response);
      return response;
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[GetFriendsHandler] Handler handle >exception< with request: {}, response: {}, ",
          baseRequestInfo, response, exception);
      return response;
    }
  }
}
