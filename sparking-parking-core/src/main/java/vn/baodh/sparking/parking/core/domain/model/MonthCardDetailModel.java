package vn.baodh.sparking.parking.core.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MonthCardDetailModel {
  private String monthCardId;
  private String locationId;
  private String locationName;
  private String locationAddress;
  private String locationImageUrl;
  private String useUserId;
  private String sourceUserId;
  private String price;
  private String number;
  private String startDate;
}
