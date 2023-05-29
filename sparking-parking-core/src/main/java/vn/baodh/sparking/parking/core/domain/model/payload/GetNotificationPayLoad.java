package vn.baodh.sparking.parking.core.domain.model.payload;

import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.baodh.sparking.parking.core.domain.model.PayLoad;

@Data
@Accessors(chain = true)
public class GetNotificationPayLoad implements PayLoad {

  private String phone;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public GetNotificationPayLoad getPayLoadInfo(Map<String, ?> params) {
    GetNotificationPayLoad payload = new GetNotificationPayLoad();
    payload.setPhone((String) params.get("phone"));

    payload.setDeviceId((String) params.get("device_id"));
    payload.setDeviceModel((String) params.get("device_model"));
    payload.setAppVersion((String) params.get("app_version"));
    return payload;
  }

  // TODO
  public boolean validatePayload() {
    return this.getPhone() != null && this.getPhone().matches("^\\d{1,20}$");
  }
}
