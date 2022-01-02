package com.cmc.y3group.ddd.app.http.controller.api.product;

import com.cmc.y3group.ddd.app.http.middleware.Authenticate;
import com.cmc.y3group.ddd.domain.subdomain.product.dto.ProductDTO;
import com.cmc.y3group.ddd.app.models.response.Response;
import com.cmc.y3group.ddd.app.service.AppProductService;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.infrastructure.support.MappingUtils;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.cmc.y3group.ddd.infrastructure.support.MappingUtils.OBJ_MAPPER;

@Slf4j
@Component
public class ProductCreateApi extends Controller {
	@Autowired
	private AppProductService appProductService;

	/**
	 * Path.
	 */
	public static final String PATH = "/product/create";

	public ProductCreateApi() {
		middlewares = new Class[]{
			Authenticate.class
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(RoutingContext evt) {
		ProductDTO productDTO = MappingUtils.requestMapping(evt, ProductDTO.class);
		productDTO.setFileUploads(evt.fileUploads());
		User user = null;
		if (!Objects.isNull(evt.user())) {
			try {
				user = OBJ_MAPPER.readValue(evt.user().principal().toString(), User.class);
			} catch (JsonProcessingException e) {
				log.error(e.getMessage(), e);
			}
		} else {
			Response res = new Response();
			res.setMessage("Requires login.");
			res.setStatus(Response.STATUS.FAILURE);
			evt.response().setStatusCode(HttpResponseStatus.UNAUTHORIZED.code());
			evt.json(res);
			return;
		}

		appProductService.create(productDTO, user)
			.onSuccess(handle -> {
				Response res = new Response();
				res.setMessage("successfully.");
				res.setCode(Response.CODE.OK);
				res.setStatus(Response.STATUS.SUCCESS);
				res.setData(handle);
				evt.json(res);
			})
			.onFailure(evt::fail);
	}
}
