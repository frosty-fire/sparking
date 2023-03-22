package vn.baodh.sparking.parking.core.domain.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseEntity {
  public static Gson gson;

  public BaseEntity() {
  }

  public String toJsonString() {
    return gson.toJson(this);
  }

  static {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    gson = gsonBuilder.disableHtmlEscaping().create();
  }
}