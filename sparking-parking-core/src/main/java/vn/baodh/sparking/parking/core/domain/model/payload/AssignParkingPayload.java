package vn.baodh.sparking.parking.core.domain.model.payload;

import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.baodh.sparking.parking.core.domain.model.PayLoad;

@Data
@Accessors(chain = true)
public class AssignParkingPayload implements PayLoad {

  private String parkingId;
  private String userId;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public AssignParkingPayload getPayLoadInfo(Map<String, ?> params) {
    AssignParkingPayload payload = new AssignParkingPayload();
    payload.setParkingId((String) params.get("parking_id"));
    payload.setUserId((String) params.get("user_id"));

    payload.setDeviceId((String) params.get("device_id"));
    payload.setDeviceModel((String) params.get("device_model"));
    payload.setAppVersion((String) params.get("app_version"));
    return payload;
  }

  // TODO
  public boolean validatePayload() {
    return this.getParkingId() != null && this.getParkingId().matches("^\\d{1,20}$") &&
        this.getUserId() != null && this.getUserId().matches("^\\d{1,20}$");
  }
}
