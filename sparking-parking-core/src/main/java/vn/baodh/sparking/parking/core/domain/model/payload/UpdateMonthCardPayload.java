package vn.baodh.sparking.parking.core.domain.model.payload;

import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.baodh.sparking.parking.core.domain.model.PayLoad;

@Data
@Accessors(chain = true)
public class UpdateMonthCardPayload implements PayLoad {

  private String monthCardId;
  private String phone;
  private String locationId;
  private String price;
  private String additionNumber;
  private String useUserId;
  private String notificationId;

  private String deviceId;
  private String deviceModel;
  private String appVersion;

  public UpdateMonthCardPayload getPayLoadInfo(Map<String, ?> params) {
    UpdateMonthCardPayload payload = new UpdateMonthCardPayload();
    payload.setMonthCardId((String) params.get("month_card_id"));
    payload.setPhone((String) params.get("phone"));
    payload.setLocationId((String) params.get("location_id"));
    payload.setPrice((String) params.get("price"));
    payload.setAdditionNumber((String) params.get("addition_number"));
    payload.setUseUserId((String) params.get("use_user_id"));

    payload.setDeviceId((String) params.get("device_id"));
    payload.setDeviceModel((String) params.get("device_model"));
    payload.setAppVersion((String) params.get("app_version"));
    return payload;
  }

  // TODO
  public boolean validatePayload() {
    return this.getMonthCardId() != null && this.getMonthCardId().matches("^\\d{1,20}$") &&
        this.getPhone() != null && this.getPhone().matches("^\\d{1,20}$") &&
        this.getLocationId() != null && this.getLocationId().matches("^\\d{1,20}$") &&
        ((this.getAdditionNumber() != null && !this.getAdditionNumber().equals("")) ||
            (this.getUseUserId() != null && !this.getUseUserId().equals("")));
  }
}
