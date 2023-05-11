package vn.baodh.sparking.parking.core.domain.model.payload;

import lombok.Data;
import lombok.experimental.Accessors;
import vn.baodh.sparking.parking.core.domain.util.GsonUtil;

@Data
@Accessors(chain = true)
public class StatusPayLoad {

  private int status;
  private String roomId;
  private String statusMessage;

  public StatusPayLoad getPayLoadInfo(String json) {
    return GsonUtil.fromJsonString(json, StatusPayLoad.class);
  }

  // TODO
  public boolean validatePayload() {
    return this.getRoomId() != null && this.getRoomId().matches("^\\d{1,20}_\\d{1,20}$");
  }
}
