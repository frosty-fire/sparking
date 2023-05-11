package vn.baodh.sparking.parking.core.domain.model.payload;

import lombok.Data;
import lombok.experimental.Accessors;
import vn.baodh.sparking.parking.core.domain.util.GsonUtil;

@Data
@Accessors(chain = true)
public class RoomPayload {

  private String roomId;

  public RoomPayload getPayLoadInfo(String json) {
    return GsonUtil.fromJsonString(json, RoomPayload.class);
  }

  // TODO
  public boolean validatePayload() {
    return this.getRoomId() != null && this.getRoomId().matches("^\\d{1,20}_\\d{1,20}$");
  }
}
