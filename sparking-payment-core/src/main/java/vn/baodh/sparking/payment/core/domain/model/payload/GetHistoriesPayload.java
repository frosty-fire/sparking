package vn.baodh.sparking.payment.core.domain.model.payload;

import java.util.Map;
import lombok.Data;
import vn.baodh.sparking.payment.core.domain.model.PayLoad;

@Data
public class GetHistoriesPayload implements PayLoad {

  private String phone;
  private String type;
  private String prevId;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public GetHistoriesPayload getPayLoadInfo(Map<String, ?> params) {
    GetHistoriesPayload payload = new GetHistoriesPayload();
    payload.setPhone((String) params.get("phone"));
    payload.setType((String) params.get("type"));
    payload.setPrevId((String) params.get("prev_id"));

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
