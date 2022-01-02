package com.cmc.y3group.ddd.config;

import com.cmc.y3group.ddd.infrastructure.system.vertx.eventbus.GenericCodec;
import com.cmc.y3group.ddd.infrastructure.system.vertx.eventbus.LocalEventBusCodec;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.micrometer.MicrometerMetricsOptions;
import io.vertx.micrometer.VertxPrometheusOptions;

import java.util.ArrayList;
import java.util.Objects;

public class VertxConfiguration {
	private static VertxConfiguration instance;

	private Vertx vertx;

	/**
	 * Default constructor.
	 */
	public VertxConfiguration(){
		MetricsOptions metricsOptions = new MicrometerMetricsOptions()
			.setPrometheusOptions(new VertxPrometheusOptions().setEnabled(true))
			.setEnabled(true);
		VertxOptions vertxOptions = new VertxOptions().setMetricsOptions(metricsOptions);

		this.vertx = Vertx.vertx(vertxOptions);
//		vertx.eventBus().registerDefaultCodec(ArrayList.class, new GenericCodec<>(ArrayList.class));
		vertx.eventBus().registerDefaultCodec(ArrayList.class, new LocalEventBusCodec<>());
	}

	public static VertxConfiguration getInstance() {
		if (Objects.isNull(instance)) {
			synchronized (VertxConfiguration.class) {
				if (Objects.isNull(instance))
					instance = new VertxConfiguration();
			}
		}
		return instance;
	}

	public Vertx getVertx() {
		return vertx;
	}
}
