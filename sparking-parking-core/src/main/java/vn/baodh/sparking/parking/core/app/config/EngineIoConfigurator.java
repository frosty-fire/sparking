package vn.baodh.sparking.parking.core.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import vn.baodh.sparking.parking.core.infra.controller.socket.EngineIoController;

@Configuration
@EnableWebSocket
public class EngineIoConfigurator implements WebSocketConfigurer {

    private final EngineIoController engineIoController;

    public EngineIoConfigurator(EngineIoController engineIoController) {
        this.engineIoController = engineIoController;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(engineIoController, "/prc/engine.io/")
            .addInterceptors(engineIoController)
            .setAllowedOrigins("*");
        registry.addHandler(engineIoController, "/prc/socket.io/")
                .addInterceptors(engineIoController)
                .setAllowedOrigins("*");
    }
}
