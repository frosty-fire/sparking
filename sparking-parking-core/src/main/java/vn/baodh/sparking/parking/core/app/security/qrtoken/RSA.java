package vn.baodh.sparking.parking.core.app.security.qrtoken;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.Cipher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.domain.model.QrModel;

@Slf4j
@Component
public class RSA {

  private PublicKey publicKey;
  private PrivateKey privateKey;

  RSA() {
    try {
      var generator = KeyPairGenerator.getInstance("RSA");
      generator.initialize(2048);
      var pair = generator.generateKeyPair();
      this.privateKey = pair.getPrivate();
      this.publicKey = pair.getPublic();
    } catch (Exception e) {
      log.info("[RSA] init key pair failed");
    }
  }

  public String encryptQr(QrModel qrModel) {
    try {
      log.info("[RSA] encrypting qr, qrModel: {}", qrModel);
      var cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      var bi = new ObjectMapper().writeValueAsBytes(qrModel);
      var biMessage = cipher.doFinal(bi);
      return new String(Base64.getEncoder().encode(biMessage));
    } catch (Exception e) {
      log.info("[RSA] encrypt qr failed, qrModel: {}", qrModel);
    }
    return null;
  }

  public QrModel decryptQr(String message) {
    try {
      log.info("[RSA] decrypting qr, message: {}", message);
      var cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      var biRevMessage = cipher.doFinal(Base64.getDecoder().decode(message));
      return new ObjectMapper().readValue(biRevMessage, QrModel.class);
    } catch (Exception e) {
      log.info("[RSA] decrypt qr failed, message: {}", message);
    }
    return null;
  }

}
