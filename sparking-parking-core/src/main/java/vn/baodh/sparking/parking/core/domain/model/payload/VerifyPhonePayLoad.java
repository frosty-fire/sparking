package vn.baodh.sparking.parking.core.domain.model.payload;

import java.util.Map;
import lombok.Data;

@Data
public class VerifyPhonePayLoad implements PayLoad {
  private String phone;
  private String otp;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public VerifyPhonePayLoad getPayLoadInfo(Map<String, ?> params) {
    VerifyPhonePayLoad payload = new VerifyPhonePayLoad();
    payload.setPhone((String) params.get("phone"));
    payload.setOtp((String) params.get("OTP"));

    payload.setDeviceId((String) params.get("device_id"));
    payload.setDeviceModel((String) params.get("device_model"));
    payload.setAppVersion((String) params.get("app_version"));
    return payload;
  }

  // TODO
  public boolean validatePayload() {
    return this.getPhone().matches("^\\d{1,20}$")
        && this.getOtp().matches("^\\d{6}$");
  }
}
