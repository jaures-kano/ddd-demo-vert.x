package com.cmc.y3group.ddd.config;

import com.cmc.y3group.ddd.config.properties.WebProperties;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;
import io.vertx.micrometer.PrometheusScrapingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class WebConfiguration {
	public static final String WEB_ROOT = "static";

	@Autowired
	private WebProperties webProperties;

	@Bean
	private CSRFHandler csrfHandler() {
		VertxConfiguration vertxConfiguration = VertxConfiguration.getInstance();
		return CSRFHandler.create(vertxConfiguration.getVertx(), "cmcglobal");
	}

	@Bean
	private SessionHandler sessionHandler() {
		VertxConfiguration vertxConfiguration = VertxConfiguration.getInstance();
		SessionStore store = LocalSessionStore.create(vertxConfiguration.getVertx());
		return SessionHandler.create(store).setCookieless(true);
	}

	@Bean
	private BodyHandler bodyHandler() {
		BodyHandler bodyHnd = BodyHandler.create();
		bodyHnd.setDeleteUploadedFilesOnEnd(true);
		return bodyHnd;
	}

	public void config(Router router) {
		router.route().handler(corsHandler());
		router.route("/metrics").handler(PrometheusScrapingHandler.create());

		StaticHandler staticHnd = StaticHandler.create(WEB_ROOT);
		staticHnd.setCachingEnabled(true);
		router.route(webProperties.getStaticPath()).handler(staticHnd);
	}

	private CorsHandler corsHandler() {
		return CorsHandler.create("*")
			.allowCredentials(true)
			.exposedHeaders(Set.of(
				"Authorization", "Content-Language", "Content-Length",
				"Cache-Control", "Content-Type", "Expires", "Last-Modified",
				"Pragma", "Content-Disposition", "Session"))
			.allowedHeader("*")
			.maxAgeSeconds(14600)
			.allowedMethods(new HashSet<>(HttpMethod.values()));
	}
}
