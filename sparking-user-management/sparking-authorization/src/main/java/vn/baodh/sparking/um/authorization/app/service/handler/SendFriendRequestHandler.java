package vn.baodh.sparking.um.authorization.app.service.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.um.authorization.app.service.FlowHandler;
import vn.baodh.sparking.um.authorization.domain.enumeration.StatusEnum;
import vn.baodh.sparking.um.authorization.domain.model.NotificationModel.ApiModel;
import vn.baodh.sparking.um.authorization.domain.model.NotificationModel.ExtraInfo;
import vn.baodh.sparking.um.authorization.domain.model.UserModel;
import vn.baodh.sparking.um.authorization.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.um.authorization.domain.model.base.BaseResponse;
import vn.baodh.sparking.um.authorization.domain.model.payload.SendFriendsRequestPayload;
import vn.baodh.sparking.um.authorization.infra.jdbc.entity.FriendEntity;
import vn.baodh.sparking.um.authorization.infra.jdbc.entity.NotificationEntity;
import vn.baodh.sparking.um.authorization.infra.jdbc.master.JdbcFriendRepository;
import vn.baodh.sparking.um.authorization.infra.jdbc.master.JdbcNotificationRepository;
import vn.baodh.sparking.um.authorization.infra.jdbc.master.JdbcUserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendFriendRequestHandler implements FlowHandler {

  private final JdbcUserRepository userRepository;
  private final JdbcFriendRepository friendRepository;
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
    BaseResponse<UserModel> response = new BaseResponse<>();
    try {
      SendFriendsRequestPayload payload = new SendFriendsRequestPayload().getPayLoadInfo(
          baseRequestInfo.getParams());
      if (payload.validatePayload()) {
        var userId = userRepository.getUserIdByPhone(payload.getPhone());
        var thatUser = userRepository.getFriendById(payload.getThatUserId(), true);
        var friendId = generateId(System.currentTimeMillis());
        var friendEntity = new FriendEntity()
            .setFriendId(friendId)
            .setThisUserId(userId)
            .setThisUserId(payload.getThatUserId())
            .setStatus("pending");
        friendRepository.create(friendEntity);
        var apiAccept = new ApiModel()
            .setApi("https://sparking.ngrok.app/um/user/friend/update-friend-request?friend_id="
                + friendId + "&status=accepted")
            .setTitle("Chấp nhận");
        var apiReject = new ApiModel()
            .setApi("https://sparking.ngrok.app/um/user/friend/update-friend-request?friend_id="
                + friendId + "&status=cancle")
            .setTitle("Từ chối");
        var notification = new NotificationEntity()
            .setNotificationId(generateId(System.currentTimeMillis()))
            .setUserId(userId)
            .setType("REQUEST")
            .setTitle(thatUser.get(0).getFullName() + " gửi yêu cầu kết bạn")
            .setSubType("")
            .setExtraInfo(new ObjectMapper().writeValueAsString(
                new ExtraInfo()
                    .setHaveApi(true)
                    .setApiList(
                        List.of(new ApiModel[]{
                            apiAccept,
                            apiReject
                        })
                    )
            ));
        notificationRepository.create(notification);
        response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
      } else {
        response.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
      }
      log.info("[SendFriendRequestHandler] Finish handle request: {}, response: {}",
          baseRequestInfo, response);
      return response;
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[SendFriendRequestHandler] Handler handle >exception< with request: {}, response: {}, ",
          baseRequestInfo, response, exception);
      return response;
    }
  }
}
