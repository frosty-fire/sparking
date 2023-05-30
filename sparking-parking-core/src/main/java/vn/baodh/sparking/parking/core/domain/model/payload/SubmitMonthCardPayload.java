package vn.baodh.sparking.parking.core.domain.model.payload;

import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.baodh.sparking.parking.core.domain.model.PayLoad;

@Data
@Accessors(chain = true)
public class SubmitMonthCardPayload implements PayLoad {

  private String phone;
  private String locationId;
  private String price;
  private String number;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public SubmitMonthCardPayload getPayLoadInfo(Map<String, ?> params) {
    SubmitMonthCardPayload payload = new SubmitMonthCardPayload();

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
