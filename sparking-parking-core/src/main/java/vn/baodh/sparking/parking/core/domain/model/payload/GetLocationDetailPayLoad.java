package vn.baodh.sparking.parking.core.domain.model.payload;

import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.baodh.sparking.parking.core.domain.model.PayLoad;

@Data
@Accessors(chain = true)
public class GetLocationDetailPayLoad implements PayLoad {

  private String locationId;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public GetLocationDetailPayLoad getPayLoadInfo(Map<String, ?> params) {
    GetLocationDetailPayLoad payload = new GetLocationDetailPayLoad();
    payload.setLocationId((String) params.get("location_id"));

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
