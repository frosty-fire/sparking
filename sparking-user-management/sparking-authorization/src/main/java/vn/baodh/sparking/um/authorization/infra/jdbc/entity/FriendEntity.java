package vn.baodh.sparking.um.authorization.infra.jdbc.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@Data
@Accessors(chain = true)
public class FriendEntity {

  @Id
  private String friendId;
  private String thisUserId;
  private String thatUserId;
  private String status;
}
