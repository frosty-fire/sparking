package vn.baodh.sparking.parking.core.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TokenModel {
  private String qrToken;
  private String expiredTime;
}
