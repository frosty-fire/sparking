package vn.baodh.sparking.parking.core.infra.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PayModel {
  private String method;
  private Params params;

  @Data
  @Accessors(chain = true)
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public static class Params {

    private String phone;
    private String locationId;
    private String price;
  }
}
