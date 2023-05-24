package vn.baodh.sparking.payment.core.domain.model;

import java.util.Map;

public interface PayLoad {
  boolean validatePayload();
  PayLoad getPayLoadInfo(Map<String, ?> params);
}
