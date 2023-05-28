package vn.baodh.sparking.parking.core.domain.enumeration;

public enum StatusEnum {

  SUCCESS(1, "Thành công"),
  UNKNOWN(0, "Lỗi không xác định, vui lòng thử lại"),
  EXCEPTION(-1, "Hệ thống đang bị lỗi, vui lòng thử lại sau"),
  INVALID_PARAMETER(-2, "Các giá trị truyền vào không hợp lệ"),
  VEHICLE_EXISTED(-3,"Mã xe đã tồn tại, vui lòng kiểm tra"),
  VEHICLE_NOT_FOUND(-4, "Mã xe không tồn tại, vui lòng kiểm tra"),
  LICENSE_NOT_MATCH(-5, "Biển số xe không đúng, vui lòng kiểm tra"),
  ;

  private final int statusCode;
  private final String statusMessage;

  StatusEnum(int statusCode, String statusMessage) {
    this.statusCode = statusCode;
    this.statusMessage = statusMessage;
  }

  public static StatusEnum getStatusEnum(int statusCode) {
    for (StatusEnum statusEnum : StatusEnum.values()) {
      if (statusEnum.getStatusCode() == statusCode) {
        return statusEnum;
      }
    }
    return EXCEPTION;
  }

  public int getStatusCode() {
    return this.statusCode;
  }

  public String getStatusMessage() {
    return this.statusMessage;
  }
}
