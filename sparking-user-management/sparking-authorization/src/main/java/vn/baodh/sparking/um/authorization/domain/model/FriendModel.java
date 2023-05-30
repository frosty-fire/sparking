package vn.baodh.sparking.um.authorization.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FriendModel {

  private String userId;
  private String phone;
  private String fullName;
  private String imageUrl;
}
