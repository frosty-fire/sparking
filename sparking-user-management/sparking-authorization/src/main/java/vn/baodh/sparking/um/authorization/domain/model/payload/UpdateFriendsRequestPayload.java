package vn.baodh.sparking.um.authorization.domain.model.payload;

import java.util.Map;
import lombok.Data;
import vn.baodh.sparking.um.authorization.domain.model.PayLoad;

@Data
public class UpdateFriendsRequestPayload implements PayLoad {

  private String friendId;
  private String status;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public UpdateFriendsRequestPayload getPayLoadInfo(Map<String, ?> params) {
    UpdateFriendsRequestPayload payload = new UpdateFriendsRequestPayload();
    payload.setFriendId((String) params.get("friend_id"));
    payload.setStatus((String) params.get("status"));

    payload.setDeviceId((String) params.get("device_id"));
    payload.setDeviceModel((String) params.get("device_model"));
    payload.setAppVersion((String) params.get("app_version"));
    return payload;
  }

  // TODO
  public boolean validatePayload() {
    return this.getFriendId() != null && this.getFriendId().matches("^\\d{1,20}$");
  }
}
