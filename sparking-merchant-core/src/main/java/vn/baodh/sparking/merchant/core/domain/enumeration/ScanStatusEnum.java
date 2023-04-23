package vn.baodh.sparking.merchant.core.domain.enumeration;

public enum ScanStatusEnum {
  // default
  FAILED(0, "failed"),
  NEED_LICENSE_PLATE(1, "need-license-plate"),
  SUBMIT(2, "submit"),
  SUCCESS(3, "success"),
  QR_ERROR(4, "qr-error"),
  QR_FAILED(5, "qr-failed"),
  LICENSE_ERROR(6, "license-error"),
  LICENSE_FAILED(7, "license-failed")
  ;

  private final int value;
  private final String status;

  ScanStatusEnum(int value, String status) {
    this.value = value;
    this.status = status;
  }

  public static ScanStatusEnum getScanStatusEnum(String status) {
    for (ScanStatusEnum scanStatusEnum : ScanStatusEnum.values()) {
      if (scanStatusEnum.getStatus().equalsIgnoreCase(status)) {
        return scanStatusEnum;
      }
    }
    return FAILED;
  }

  public int getValue() {
    return this.value;
  }

  public String getStatus() {
    return this.status;
  }
}