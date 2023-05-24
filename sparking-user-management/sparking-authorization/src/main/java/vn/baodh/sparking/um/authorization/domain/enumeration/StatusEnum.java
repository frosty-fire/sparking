package vn.baodh.sparking.um.authorization.domain.enumeration;

public enum StatusEnum {

  SUCCESS(1, "Thành công"),
  UNKNOWN(0, "Lỗi không xác định, vui lòng thử lại"),
  EXCEPTION(-1, "Hệ thống đang bị lỗi, vui lòng thử lại sau"),
  INVALID_PARAMETER(-2, "Các giá trị truyền vào không hợp lệ"),
  DUPLICATE_PHONE(-3, "Số điện thoại đã tồn tại"),
  USER_NOT_FOUND(-4, "Tài khoản không tồn tại"),
  WRONG_OTP(-5, "OTP không hợp lệ, mời nhập lại"),
  LOGIN_FAILED(-6, "Thông tin chưa đúng, mời nhập lại"),
  WRONG_INFO(-7, "Thông tin chưa đúng, mời nhập lại"),
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
