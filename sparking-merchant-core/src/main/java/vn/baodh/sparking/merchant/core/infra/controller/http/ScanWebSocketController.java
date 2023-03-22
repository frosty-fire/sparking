package vn.baodh.sparking.merchant.core.infra.controller.http;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import vn.baodh.sparking.merchant.core.domain.model.User;
import vn.baodh.sparking.merchant.core.domain.model.UserResponse;

@Controller
public class ScanWebSocketController {

  @MessageMapping("/license-plate-scan")
  @SendTo("/room/mock")
  public UserResponse getUser1(User user) {

    return new UserResponse().setContent(user.getMessage());

  }

  @MessageMapping("/qr-scan")
  @SendTo("/room/mock")
  public UserResponse getUser2(User user) {

    return new UserResponse().setContent(user.getMessage());

  }
}
