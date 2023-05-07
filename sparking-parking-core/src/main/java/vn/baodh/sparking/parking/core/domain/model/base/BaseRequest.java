package vn.baodh.sparking.parking.core.domain.model.base;

import java.util.Map;
import lombok.Data;

@Data
public class BaseRequest {
  private String method;
  private Map<String, String> params;
}
