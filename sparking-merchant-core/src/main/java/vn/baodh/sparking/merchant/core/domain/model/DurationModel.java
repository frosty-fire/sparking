package vn.baodh.sparking.merchant.core.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DurationModel {
  private int hours;
  private int minutes;
  private int seconds;
  private int milliseconds;
}
