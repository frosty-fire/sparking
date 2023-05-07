package vn.baodh.sparking.um.authorization.domain.model;

import java.util.Map;

public interface PayLoad {
  boolean validatePayload();
  PayLoad getPayLoadInfo(Map<String, ?> params);
}
