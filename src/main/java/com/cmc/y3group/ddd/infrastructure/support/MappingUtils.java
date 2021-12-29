package com.cmc.y3group.ddd.infrastructure.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public final class MappingUtils {
	/**
	 * Object mapper.
	 */
	public static ObjectMapper OBJ_MAPPER = new ObjectMapper();

	static {
		OBJ_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJ_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	/**
	 * @param ctx   Context.
	 * @param clazz Clazz.
	 */
	public static <T> T requestMapping(RoutingContext ctx, Class<T> clazz) {
		return requestMapping(ctx, clazz, false);
	}

	public static <T> T requestMapping(RoutingContext ctx, Class<T> clazz, boolean createNew) {
		MultiMap form = ctx.request().formAttributes();
		MultiMap params = ctx.request().params();
		form.addAll(params);
		T obj = null;
		try {
			if (!form.isEmpty()) {
				JsonObject metadata = new JsonObject();
				for (Map.Entry<String, String> entry : form.entries())
					metadata.put(entry.getKey(), entry.getValue());

				obj = OBJ_MAPPER.readValue(metadata.toString(), clazz);
			} else {
				Buffer bodyBuff = ctx.getBody();
				if (!Objects.isNull(bodyBuff)) obj = OBJ_MAPPER.readValue(bodyBuff.toJsonObject().toString(), clazz);
			}
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}
		try {
			return createNew && Objects.isNull(obj) ? clazz.newInstance() : obj;
		} catch (InstantiationException | IllegalAccessException e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}

	public static <T> T reqParam(RoutingContext ctx, Class<T> clazz) {
		MultiMap params = ctx.request().params();
		T obj = null;
		try {
			if (!params.isEmpty()) {
				JsonObject metadata = new JsonObject();
				for (Map.Entry<String, String> entry : params.entries())
					metadata.put(entry.getKey(), entry.getValue());

				obj = OBJ_MAPPER.readValue(metadata.toString(), clazz);
			} else {
				Buffer bodyBuff = ctx.getBody();
				if (!Objects.isNull(bodyBuff)) obj = OBJ_MAPPER.readValue(bodyBuff.toJsonObject().toString(), clazz);
			}
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}
		try {
			return Objects.isNull(obj) ? clazz.newInstance() : obj;
		} catch (InstantiationException | IllegalAccessException e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}
}
