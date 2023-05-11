package vn.baodh.sparking.parking.core.domain.model.payload;

import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.baodh.sparking.parking.core.domain.model.PayLoad;

@Data
@Accessors(chain = true)
public class CheckOutPayload implements PayLoad {

  private String vehicleId;
  private String voucherId;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public CheckOutPayload getPayLoadInfo(Map<String, ?> params) {
    CheckOutPayload payload = new CheckOutPayload();
    payload.setVehicleId((String) params.get("vehicle_id"));
    payload.setVoucherId((String) params.get("voucher_id_applying"));

    payload.setDeviceId((String) params.get("device_id"));
    payload.setDeviceModel((String) params.get("device_model"));
    payload.setAppVersion((String) params.get("app_version"));
    return payload;
  }

  // TODO
  public boolean validatePayload() {
    return this.getVehicleId() != null && this.getVehicleId().matches("^\\d{1,20}$");
  }
}
