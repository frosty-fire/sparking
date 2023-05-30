package vn.baodh.sparking.um.authorization.domain.model.payload;

import java.util.Map;
import lombok.Data;
import vn.baodh.sparking.um.authorization.domain.model.PayLoad;

@Data
public class GetFriendsPayload implements PayLoad {
  private String thisPhone;
  private String phoneSearching;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public GetFriendsPayload getPayLoadInfo(Map<String, ?> params) {
    GetFriendsPayload payload = new GetFriendsPayload();
    payload.setThisPhone((String) params.get("this_phone"));
    payload.setPhoneSearching((String) params.get("phone_searching"));

    payload.setDeviceId((String) params.get("device_id"));
    payload.setDeviceModel((String) params.get("device_model"));
    payload.setAppVersion((String) params.get("app_version"));
    return payload;
  }

  // TODO
  public boolean validatePayload() {
    return this.getThisPhone() != null && this.getThisPhone().matches("^\\d{1,20}$");
  }
}
