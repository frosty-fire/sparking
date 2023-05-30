package vn.baodh.sparking.parking.core.domain.enumeration;

public enum FlowEnum {
  // default
  UNKNOWN(0, "unknown"),

  // qr
  CHECK_IN(1, "gen-qr/check-in"),
  CHECK_OUT(2, "gen-qr/check-out"),

  // parking
  GET_VEHICLE(3, "parking/get-vehicle"),
  GET_VEHICLES(4, "parking/get-vehicles"),
  GET_VOUCHERS(5, "voucher/get-vouchers"),

  // main
  SUBMIT_QR(6, "parking/submit-qr"),

  // notification
  GET_NOTIFICATIONS(7, "notify/get-notifications"),

  // location
  GET_LOCATIONS(8, "location/get-locations"),
  GET_LOCATION(9, "location/get-location-detail"),

  // month card
  SUBMIT_MONTH_CARD(10, "location/submit-month-card"),
  UPDATE_MONTH_CARD(11, "location/update-month-card"),
  GET_MONTH_CARD(12, "location/get-month-card"),
  ;

  private final int value;
  private final String flowName;

  FlowEnum(int value, String flowName) {
    this.value = value;
    this.flowName = flowName;
  }

  public static FlowEnum getFlowEnum(String flowName) {
    for (FlowEnum flowEnum : FlowEnum.values()) {
      if (flowEnum.getFlowName().equalsIgnoreCase(flowName)) {
        return flowEnum;
      }
    }
    return UNKNOWN;
  }

  public int getValue() {
    return this.value;
  }

  public String getFlowName() {
    return this.flowName;
  }
}
