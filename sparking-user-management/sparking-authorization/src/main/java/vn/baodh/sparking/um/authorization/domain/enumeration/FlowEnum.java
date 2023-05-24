package vn.baodh.sparking.um.authorization.domain.enumeration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum FlowEnum {
  // default
  UNKNOWN(0, "unknown"),

  // auth
  CHECK_PHONE(1, "user/auth/check-phone"),
  VERIFY_PHONE(2, "user/auth/verify-phone"),
  LOGIN(3, "user/auth/login"),
  SIGN_UP(4, "user/auth/sign-up"),

  // profile
  GET_USER(5, "user/profile/get-user"),
  SET_NEW_PASSWORD(6, "user/profile/set-new-password"),

  // friend
  GET_FRIENDS(7, "user/friend/get-friends"),
  SEND_FRIEND_REQUEST(8, "user/friend/send-friend-request"),
  UPDATE_FRIEND_REQUEST(9, "user/friend/update-friend-request");

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
    log.info(String.valueOf(GET_FRIENDS));
    log.info(String.valueOf(SEND_FRIEND_REQUEST));
    log.info(String.valueOf(UPDATE_FRIEND_REQUEST));
    return UNKNOWN;
  }

  public int getValue() {
    return this.value;
  }

  public String getFlowName() {
    return this.flowName;
  }
}
