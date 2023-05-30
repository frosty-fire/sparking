package vn.baodh.sparking.um.authorization.domain.model.payload;

import java.util.Map;
import lombok.Data;
import vn.baodh.sparking.um.authorization.domain.model.PayLoad;

@Data
public class SendFriendsRequestPayload implements PayLoad {

  private String phone;
  private String thatUserId;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public SendFriendsRequestPayload getPayLoadInfo(Map<String, ?> params) {
    SendFriendsRequestPayload payload = new SendFriendsRequestPayload();
    payload.setPhone((String) params.get("phone"));
    payload.setThatUserId((String) params.get("that_user_id"));

    payload.setDeviceId((String) params.get("device_id"));
    payload.setDeviceModel((String) params.get("device_model"));
    payload.setAppVersion((String) params.get("app_version"));
    return payload;
  }

  // TODO
  public boolean validatePayload() {
    return this.getPhone() != null && this.getPhone().matches("^\\d{1,20}$")
        && this.getThatUserId() != null && this.getThatUserId().matches("^\\d{1,20}$");
  }
}
