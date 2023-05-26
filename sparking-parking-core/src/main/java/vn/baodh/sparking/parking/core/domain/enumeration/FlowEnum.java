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
  ENTER_PARKING(6, "parking/enter"),
  EXIT_PARKING(7, "parking/exit")
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
