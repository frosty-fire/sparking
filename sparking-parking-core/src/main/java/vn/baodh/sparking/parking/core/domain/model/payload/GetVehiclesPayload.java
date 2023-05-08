package vn.baodh.sparking.parking.core.domain.model.payload;

import java.util.Map;
import lombok.Data;
import vn.baodh.sparking.parking.core.domain.model.PayLoad;

@Data
public class GetVehiclesPayload implements PayLoad {

  private String phone;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public GetVehiclesPayload getPayLoadInfo(Map<String, ?> params) {
    GetVehiclesPayload payload = new GetVehiclesPayload();
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
