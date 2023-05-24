package vn.baodh.sparking.um.authorization.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TokenModel {
  private String accessToken;
  private boolean needOtp;
}
