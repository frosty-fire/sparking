package vn.baodh.sparking.merchant.core.domain.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubmitQrRequest {

  private String method = "submit-qr";
  private Params params;

  @Data
  @Accessors(chain = true)
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public static class Params {

    private String locationId = "20230500000000000001";
    private String qrToken;
    private String licensePlate;
    private String deviceId = "Merchant Core";
    private String deviceModel = "Merchant Core";
    private String appVersion = "1.0.0";
  }
}


