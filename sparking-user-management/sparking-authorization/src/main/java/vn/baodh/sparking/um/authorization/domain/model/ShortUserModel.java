package vn.baodh.sparking.um.authorization.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ShortUserModel {

  private String name;
  private String phone;
  private String birthDay;
  private String email;
}
