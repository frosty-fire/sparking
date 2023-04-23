package vn.baodh.sparking.merchant.core.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ScanModel {
  private String status;
  private String qrToken;
  private String licensePlate;
}
