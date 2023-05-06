package vn.baodh.sparking.um.authorization.app.security;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenChecker {
  public String getPhone(String accessToken) {
    return new String(Base64.getDecoder().decode(accessToken));
  }

  public String checkAndGetPhone(HttpServletRequest uri) {
    try {
      String accessToken = uri.getHeader("Authorization").replace("Bearer ", "");
      return this.getPhone(accessToken);
    } catch (Exception e) {
      return null;
    }
  }
}
