package vn.baodh.sparking.parking.core.domain.model;

import java.util.Map;

public interface PayLoad {
  boolean validatePayload();
  PayLoad getPayLoadInfo(Map<String, ?> params);
}
