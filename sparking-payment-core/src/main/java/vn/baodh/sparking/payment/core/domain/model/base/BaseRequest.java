package vn.baodh.sparking.payment.core.domain.model.base;

import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseRequest {

  private String method;
  private Map<String, String> params;
}
