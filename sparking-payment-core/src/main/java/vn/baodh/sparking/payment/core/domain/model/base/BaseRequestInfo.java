package vn.baodh.sparking.payment.core.domain.model.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BaseRequestInfo<T> extends BaseEntity {

  private final Map<String, T> params;
  private String methodName;

  public BaseRequestInfo(Map<String, T> params) {
    this.params = params;
  }

  public BaseRequestInfo(String methodName, Map<String, T> params) {
    this.params = params;
    this.methodName = methodName;
  }

  public String getParam(String param) {
    T value = this.params.get(param);
    if (value == null) {
      return "";
    } else {
      String result;
      if (value instanceof String) {
        result = (String) value;
      } else {
        result = ((String[]) value)[0];
      }

      if (result == null) {
        result = "";
      }

      return result;
    }
  }

  public String getPostData() {
    return null;
  }

  public String getMethodName() {
    return this.methodName;
  }

  public Map<String, String> getParams() {
    Map<String, String> p = new HashMap();
    Iterator var2 = this.params.entrySet().iterator();

    while (var2.hasNext()) {
      Map.Entry<String, T> entry = (Map.Entry) var2.next();
      p.put(entry.getKey(), this.getParam(entry.getKey()));
    }

    return p;
  }

  public int getOrDefault(String param, int defaultValue) {
    try {
      String value = this.getParam(param);
      return Integer.parseInt(value);
    } catch (Exception var4) {
      return defaultValue;
    }
  }

  public long getOrDefault(String param, long defaultValue) {
    try {
      String value = this.getParam(param);
      return Long.parseLong(value);
    } catch (Exception var5) {
      return defaultValue;
    }
  }
}
