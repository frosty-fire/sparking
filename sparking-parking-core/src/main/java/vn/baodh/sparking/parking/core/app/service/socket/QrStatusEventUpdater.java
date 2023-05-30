package vn.baodh.sparking.parking.core.app.service.socket;

import io.socket.client.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import vn.baodh.sparking.parking.core.app.constant.BeanConstants;
import vn.baodh.sparking.parking.core.domain.model.base.BaseResponse;
import vn.baodh.sparking.parking.core.domain.model.payload.RoomPayload;
import vn.baodh.sparking.parking.core.domain.model.payload.StatusPayLoad;
import vn.baodh.sparking.parking.core.domain.util.GsonUtil;

@Slf4j
@Component
@RequiredArgsConstructor
public class QrStatusEventUpdater {

  @Qualifier(BeanConstants.SOCKET_IO_CLIENT_QR_STATUS_UPDATER)
  private final Socket socket;

  public boolean handle(StatusPayLoad status) {
    AtomicBoolean a = new AtomicBoolean(false);

    socket.emit("joinRoom",
        GsonUtil.toJsonString(new RoomPayload().setRoomId(status.getRoomId())));

    socket.on("setUp", args -> {
      BaseResponse<?> res = GsonUtil.fromJsonString(String.valueOf(args[0]), BaseResponse.class);
      if (res.getReturnCode() < 1) {
        status.setStatus(res.getReturnCode());
        status.setStatusMessage("Lỗi server không thể kết nối");
      }
      socket.emit("updateStatus", GsonUtil.toJsonString(status));
      log.info(GsonUtil.toJsonString(status));
      socket.emit("leaveRoom", GsonUtil.toJsonString(status.getRoomId()));
    });

    socket.on("afterUpdate", args -> {
      BaseResponse<?> res = GsonUtil.fromJsonString(String.valueOf(args[0]), BaseResponse.class);
      if (res.getReturnCode() < 1) {
        log.error("[QrStatusEventUpdater][afterUpdate] failed, reason: {}, data: {}", res.getReturnMessage(), res.getData());
      } else {
        a.set(true);
        log.info("[QrStatusEventUpdater][afterUpdate] success, reason: {}, data: {}", res.getReturnMessage(), res.getData());
      }
    });

    socket.on("tearDown", args -> {
      BaseResponse<?> res = GsonUtil.fromJsonString(String.valueOf(args[0]), BaseResponse.class);
      if (res.getReturnCode() < 1) {
        log.error("[QrStatusEventUpdater][tearDown] failed, reason: {}, data: {}", res.getReturnMessage(), res.getData());
      } else {
        log.error("[QrStatusEventUpdater][tearDown] success, reason: {}, data: {}", res.getReturnMessage(), res.getData());
      }
    });

    Awaitility.await().atMost(10, TimeUnit.SECONDS).until(
        a::get
    );
    return a.get();
  }
}
