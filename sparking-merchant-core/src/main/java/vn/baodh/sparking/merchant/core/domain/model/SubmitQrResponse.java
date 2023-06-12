package vn.baodh.sparking.merchant.core.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SubmitQrResponse {

  private int returnCode = 0;
  private String returnMessage = "";
  private String data = "";
}
