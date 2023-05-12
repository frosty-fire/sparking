package vn.baodh.sparking.parking.core.app.service.socket;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;
import io.socket.engineio.server.EngineIoServer;
import io.socket.engineio.server.EngineIoServerOptions;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;
import java.net.URISyntaxException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.baodh.sparking.parking.core.app.constant.BeanConstants;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.parking.core.domain.model.base.BaseResponse;
import vn.baodh.sparking.parking.core.domain.model.payload.RoomPayload;
import vn.baodh.sparking.parking.core.domain.model.payload.StatusPayLoad;
import vn.baodh.sparking.parking.core.domain.util.GsonUtil;

@Slf4j
@Configuration
public class QrStatusEventHandler {

  public final String NAMESPACE = "/qr-status";

  @Bean(BeanConstants.SOCKET_IO_CLIENT_QR_STATUS_UPDATER)
  public Socket socketIoClient() throws URISyntaxException {
    IO.Options options = IO.Options.builder()
        .setPath("/prc/socket.io")
        .setTransports(new String[]{WebSocket.NAME})
        .setRememberUpgrade(true)
        .build();
    Socket socket = IO.socket("http://localhost:8021" + NAMESPACE, options);
    socket.connect();
    socket.on("connect", args -> {
      log.info("[QrStatusEventHandler] SocketUpdater connected");
    });
    return socket;
  }

  @Bean(BeanConstants.ENGINE_IO_SERVER_QR_STATUS_HANDLER)
  EngineIoServer engineIoServer() {
    var opt = EngineIoServerOptions.newFromDefault();
    opt.setCorsHandlingDisabled(true);
    return new EngineIoServer(opt);
  }

  @Bean(BeanConstants.SOCKET_IO_SERVER_QR_STATUS_HANDLER)
  SocketIoServer socketIoServer(
      @Qualifier(BeanConstants.ENGINE_IO_SERVER_QR_STATUS_HANDLER)
      EngineIoServer eioServer
  ) {
    var sioServer = new SocketIoServer(eioServer);
    var namespace = sioServer.namespace(NAMESPACE);

    namespace.on("connection", args -> {
      SocketIoSocket socket = (SocketIoSocket) args[0];

      if (socket == null) {
        log.error("[QrStatusEventHandler] Socket connection is null, args: {}",
            Arrays.toString(args));
      } else {
        log.info("[QrStatusEventHandler] Socket connected, args: {}, id: {}",
            args, socket.getId());

        socket.on("joinRoom", subArgs -> {
          var room = new RoomPayload().getPayLoadInfo(String.valueOf(subArgs[0]));
          var res = new BaseResponse<>();
          if (room != null && room.validatePayload()) {
            socket.joinRoom(room.getRoomId());
            res.updateResponse(StatusEnum.SUCCESS.getStatusCode());
          } else {
            res.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
          }
          socket.send("setUp", GsonUtil.toJsonString(res));
        });

        socket.on("updateStatus", subArgs -> {
          var status = new StatusPayLoad().getPayLoadInfo(String.valueOf(subArgs[0]));
          var res = new BaseResponse<StatusPayLoad>();
          if (status != null && status.validatePayload()) {
            res.setData(new StatusPayLoad[]{status});
            res.updateResponse(StatusEnum.SUCCESS.getStatusCode());
            socket.broadcast(
                status.getRoomId(),
                "statusListeners",
                GsonUtil.toJsonString(res)
            );
          } else {
            res.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
          }
          socket.send("afterUpdate", GsonUtil.toJsonString(res));
        });

        socket.on("leaveRoom", subArgs -> {
          var room = new RoomPayload().getPayLoadInfo(String.valueOf(subArgs[0]));
          var res = new BaseResponse<>();
          if (room != null && room.validatePayload()) {
            socket.leaveRoom(room.getRoomId());
            res.updateResponse(StatusEnum.SUCCESS.getStatusCode());
          } else {
            res.updateResponse(StatusEnum.INVALID_PARAMETER.getStatusCode());
          }
          socket.send("tearDown", GsonUtil.toJsonString(res));
        });
      }
    });

    return sioServer;
  }
}
