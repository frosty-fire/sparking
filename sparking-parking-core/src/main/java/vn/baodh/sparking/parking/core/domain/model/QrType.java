package vn.baodh.sparking.parking.core.domain.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public enum QrType {
  QR_CHECK_IN,
  QR_CHECK_OUT
}
