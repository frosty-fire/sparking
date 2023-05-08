package vn.baodh.sparking.parking.core.domain.model;

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
