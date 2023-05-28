package vn.baodh.sparking.parking.core.domain.model.payload;

import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.baodh.sparking.parking.core.domain.model.PayLoad;

@Data
@Accessors(chain = true)
public class CheckOutPayload implements PayLoad {

  private String socketKey;
  private String phone;
  private String vehicleId;
  private String voucherId;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public CheckOutPayload getPayLoadInfo(Map<String, ?> params) {
    CheckOutPayload payload = new CheckOutPayload();
    payload.setSocketKey((String) params.get("socket_key"));
    payload.setPhone((String) params.get("phone"));
    payload.setVehicleId((String) params.get("vehicle_id"));
    payload.setVoucherId((String) params.get("voucher_id_applying"));

    payload.setDeviceId((String) params.get("device_id"));
    payload.setDeviceModel((String) params.get("device_model"));
    payload.setAppVersion((String) params.get("app_version"));
    return payload;
  }

  // TODO
  public boolean validatePayload() {
    return this.getPhone() != null && this.getPhone().matches("^\\d{1,20}$") &&
        this.getVehicleId() != null && this.getVehicleId().matches("^\\d{1,20}$");
  }
}
