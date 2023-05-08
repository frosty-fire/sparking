package vn.baodh.sparking.parking.core.domain.model.payload;

import java.util.Map;
import lombok.Data;
import vn.baodh.sparking.parking.core.domain.model.PayLoad;

@Data
public class GetNewVoucherPayload implements PayLoad {

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public GetNewVoucherPayload getPayLoadInfo(Map<String, ?> params) {
    GetNewVoucherPayload payload = new GetNewVoucherPayload();

    payload.setDeviceId((String) params.get("device_id"));
    payload.setDeviceModel((String) params.get("device_model"));
    payload.setAppVersion((String) params.get("app_version"));
    return payload;
  }

  // TODO
  public boolean validatePayload() {
    return true;
  }
}
