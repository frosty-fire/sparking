package vn.baodh.sparking.merchant.core.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class VoucherModel {
  private String voucherId;
  private String title;
  private String imageUrl;
}
