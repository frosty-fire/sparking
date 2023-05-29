package vn.baodh.sparking.parking.core.domain.model.payload;

import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.baodh.sparking.parking.core.domain.model.PayLoad;

@Data
@Accessors(chain = true)
public class QrSubmitPayload implements PayLoad {

  private String qrToken;
  private String licensePlate;
  private String locationId;

  public QrSubmitPayload getPayLoadInfo(Map<String, ?> params) {
    QrSubmitPayload payload = new QrSubmitPayload();
    payload.setQrToken((String) params.get("qr_token"));
    payload.setLicensePlate((String) params.get("license_plate"));
    payload.setLocationId((String) params.get("location_id"));
    return payload;
  }

  public boolean validatePayload() {
    return qrToken != null && licensePlate != null;
  }
}
