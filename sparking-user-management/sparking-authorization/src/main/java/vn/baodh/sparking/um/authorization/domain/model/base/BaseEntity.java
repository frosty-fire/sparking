package vn.baodh.sparking.um.authorization.domain.model.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseEntity {

  public static Gson gson;

  static {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    gson = gsonBuilder.disableHtmlEscaping().create();
  }

  public BaseEntity() {
  }

  public String toJsonString() {
    return gson.toJson(this);
  }
}
