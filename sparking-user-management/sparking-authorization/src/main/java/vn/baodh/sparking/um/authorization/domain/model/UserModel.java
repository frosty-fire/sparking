package vn.baodh.sparking.um.authorization.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserModel {

  private String userId;
  private String phone;
  private String pin;
  private String fullName;
  private String gender;
  private String birthday;
  private String email;
  private String cic;
  private String imageUrl;
  private String appConfig;
  private String createdAt;
  private String updatedAt;

  public ShortUserModel toShort() {
    return new ShortUserModel()
        .setName(this.getFullName())
        .setPhone(this.getPhone())
        .setImageUrl(this.getImageUrl())
        .setBirthDay(this.getBirthday())
        .setEmail(this.getEmail());
  }
}
