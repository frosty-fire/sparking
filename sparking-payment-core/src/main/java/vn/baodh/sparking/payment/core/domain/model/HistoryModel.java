package vn.baodh.sparking.payment.core.domain.model;

import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class HistoryModel {

  private String historyId;
  private String userId;
  private String type;
  private String title;
  private ExtraInfo extraInfo;
  private String createdAt;
  private String updatedAt;

  @Data
  @Accessors
  public static class ExtraInfo {
    private String description;
    private String price;
  }
}
