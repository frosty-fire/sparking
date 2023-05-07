package vn.baodh.sparking.um.authorization.domain.model.payload;

import java.util.Map;
import lombok.Data;
import vn.baodh.sparking.um.authorization.domain.model.PayLoad;

@Data
public class LoginPayLoad implements PayLoad {
  private String phone;
  private String pin;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public LoginPayLoad getPayLoadInfo(Map<String, ?> params) {
    LoginPayLoad payload = new LoginPayLoad();
    payload.setPhone((String) params.get("phone"));
    payload.setPin((String) params.get("pin"));

    payload.setDeviceId((String) params.get("device_id"));
    payload.setDeviceModel((String) params.get("device_model"));
    payload.setAppVersion((String) params.get("app_version"));
    return payload;
  }

  // TODO: need update
  public boolean validatePayload() {
    return this.getPhone().matches("^\\d{1,20}$")
        && this.getPin().matches("^\\d{6}$");
  }
}
