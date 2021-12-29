package com.cmc.y3group.ddd.infrastructure.system.vertx.http;

import com.cmc.y3group.ddd.config.properties.WebProperties;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.router.RouterInit;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("webApplication")
public class WebApplicationVerticle extends AbstractVerticle {
	private HttpServer server;
	private Router router;

	private RouterInit routerInit;

	@Autowired
	private WebProperties webProperties;

	public WebApplicationVerticle(RouterInit routerInit) {
		this.routerInit = routerInit;
	}

	@Override
	public void start(Promise<Void> startPromise) {
		this.router = Router.router(vertx);
		this.routerInit.init(this.router);

		HttpServerOptions options = new HttpServerOptions()
			.setSsl(false)
			.setPort(webProperties.getPort())
			.setCompressionSupported(true)
			.setMaxHeaderSize(32 * 1024);

		this.server = vertx.createHttpServer(options);
		this.server.requestHandler(router);

		this.server.listen(http -> {
			if (http.succeeded()) {
				log.info("HTTP server started on port " + webProperties.getPort());
				startPromise.complete();
			} else {
				log.error(http.cause().getMessage(), http.cause());
				startPromise.fail(http.cause());
			}
		});
	}

	@Override
	public void stop() {
		System.out.println("Stop HTTP ........");
	}
}
