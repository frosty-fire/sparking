package vn.baodh.sparking.um.authorization.infra.jdbc.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import vn.baodh.sparking.um.authorization.domain.model.UserModel;

@Data
@Accessors(chain = true)
public class UserEntity {

  @Id
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

  public static UserEntity from(UserModel userModel) {
    return new UserEntity()
        .setUserId(userModel.getUserId())
        .setPhone(userModel.getPhone())
        .setPin(userModel.getPin())
        .setFullName(userModel.getFullName())
        .setGender(userModel.getGender())
        .setBirthday(userModel.getBirthday())
        .setEmail(userModel.getEmail())
        .setCic(userModel.getCic())
        .setImageUrl(userModel.getImageUrl())
        .setAppConfig(userModel.getAppConfig())
        .setCreatedAt(userModel.getCreatedAt())
        .setUpdatedAt(userModel.getUpdatedAt());
  }

  public UserModel toUserModel() {
    return new UserModel()
        .setUserId(this.getUserId())
        .setPhone(this.getPhone())
        .setPin(this.getPin())
        .setFullName(this.getFullName())
        .setGender(this.getGender())
        .setBirthday(this.getBirthday())
        .setEmail(this.getEmail())
        .setCic(this.getCic())
        .setImageUrl(this.getImageUrl())
        .setAppConfig(this.getAppConfig())
        .setCreatedAt(this.getCreatedAt())
        .setUpdatedAt(this.getUpdatedAt());
  }
}
