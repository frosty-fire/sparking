package vn.baodh.sparking.payment.core.domain.model;

import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class HistoryModel {

  private String historyId;
  private String type;
  private String title;
  private Map<String, String> extraInfo;
  private String time;
}
