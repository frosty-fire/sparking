package vn.baodh.sparking.um.authorization.domain.enumeration;

public enum FlowEnum {
  // default
  UNKNOWN(0, "unknown"),

  // auth
  CHECK_PHONE(1, "auth/check-phone"),
  VERIFY_PHONE(2, "auth/verify-phone"),
  LOGIN(3, "auth/login"),
  SIGN_UP(4, "auth/sign-up"),

  // profile
  GET_USER(5, "profile/get-user");
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
