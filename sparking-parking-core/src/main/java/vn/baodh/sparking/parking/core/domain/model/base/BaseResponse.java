package vn.baodh.sparking.parking.core.domain.model.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseResponse<T> extends BaseEntity {

  private int returnCode = 0;
  private String returnMessage = "";
  private T[] data;

  public void updateResponse(int statusCode) {
    this.returnCode = statusCode;
    this.returnMessage = StatusEnum.getStatusEnum(statusCode).getStatusMessage();
  }
}
