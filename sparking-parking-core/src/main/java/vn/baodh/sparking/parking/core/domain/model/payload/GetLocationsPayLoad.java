package vn.baodh.sparking.parking.core.domain.model.payload;

import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.baodh.sparking.parking.core.domain.model.PayLoad;

@Data
@Accessors(chain = true)
public class GetLocationsPayLoad implements PayLoad {

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public GetLocationsPayLoad getPayLoadInfo(Map<String, ?> params) {
    GetLocationsPayLoad payload = new GetLocationsPayLoad();

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
