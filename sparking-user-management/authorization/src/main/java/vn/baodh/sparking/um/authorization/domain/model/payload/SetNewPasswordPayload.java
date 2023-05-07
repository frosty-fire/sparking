package vn.baodh.sparking.um.authorization.domain.model.payload;

import java.util.Map;
import java.util.Objects;
import lombok.Data;
import vn.baodh.sparking.um.authorization.domain.model.PayLoad;

@Data
public class SetNewPasswordPayload implements PayLoad {
  private String phone;
  private String isRequireOld;
  private String oldPin;
  private String newPin;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public SetNewPasswordPayload getPayLoadInfo(Map<String, ?> params) {
    SetNewPasswordPayload payload = new SetNewPasswordPayload();
    payload.setPhone((String) params.get("phone"));
    payload.setIsRequireOld((String) params.get("is-require-old"));
    payload.setOldPin((String) params.get("old-pin"));
    payload.setNewPin((String) params.get("new-pin"));

    payload.setDeviceId((String) params.get("device_id"));
    payload.setDeviceModel((String) params.get("device_model"));
    payload.setAppVersion((String) params.get("app_version"));
    return payload;
  }

  // TODO: need update
  public boolean validatePayload() {
    return this.getPhone().matches("^\\d{1,20}$")
        && (Objects.equals(this.getIsRequireOld(), "false")
        || this.getOldPin().matches("^\\d{6}$"))
        && this.getNewPin().matches("^\\d{6}$");
  }
}
