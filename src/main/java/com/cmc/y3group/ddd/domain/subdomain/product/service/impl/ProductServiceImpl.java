package com.cmc.y3group.ddd.domain.subdomain.product.service.impl;

import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.domain.event.DomainEvents;
import com.cmc.y3group.ddd.domain.event.Event;
import com.cmc.y3group.ddd.domain.event.EventBuilder;
import com.cmc.y3group.ddd.domain.subdomain.product.cache.ProductCache;
import com.cmc.y3group.ddd.domain.subdomain.product.dto.ProductDTO;
import com.cmc.y3group.ddd.domain.subdomain.product.event.ProductDomainEvent;
import com.cmc.y3group.ddd.domain.subdomain.product.factories.ImageBuilder;
import com.cmc.y3group.ddd.domain.subdomain.product.factories.ProductBuilder;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Image;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import com.cmc.y3group.ddd.domain.subdomain.product.repositories.ProductRepository;
import com.cmc.y3group.ddd.domain.subdomain.product.service.ProductService;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.FileUpload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;

import static com.cmc.y3group.ddd.config.WebConfiguration.WEB_ROOT;

@Slf4j
@Component
public class ProductServiceImpl implements ProductService {
	/**
	 * Static image.
	 */
	public static final String STATIC_IMAGE = "/assets/products/%s/image/%s.%s";


	@Autowired
	private ProductCache productCache;

	@Autowired
	private ProductRepository productRpository;

	public ProductServiceImpl() {
		DomainEvents.evtBus.consumer(ProductDomainEvent.TOPIC, this::onProductEvent);
	}

	private void onProductEvent(Message<ArrayList<Event>> msg) {
		ArrayList<Event> events = msg.body();

		ArrayList<Product> productCreateOrUpdate = new ArrayList<>();
		ArrayList<Product> productDelete = new ArrayList<>();

		for (Event event : events) {
			if (Event.TYPE.CREATE.equals(event.getType()) ||
				Event.TYPE.UPDATE.equals(event.getType())
			)
				productCreateOrUpdate.add((Product) event.getBody());
			else if (Event.TYPE.DELETE.equals(event.getType()))
				productDelete.add((Product) event.getBody());
		}

		if (!productCreateOrUpdate.isEmpty())
			productRpository.saveAll(productCreateOrUpdate);
		if (!productDelete.isEmpty())
			productRpository.deleteAll(productDelete);
	}

	@Override
	public Page<Product> findByFilter(AppFilter filter) {
		int pageNumber = Math.max(0, filter.getPageNumber() - 1);
		Pageable pageable = PageRequest.of(pageNumber, filter.getPageSize());
		Page<Product> page = productRpository.findAll(pageable);
		return page;
	}

	@Override
	public Product create(ProductDTO productDTO, User user) {
		ProductBuilder productBuilder = ProductBuilder.builder()
			.setUserId(user.getId())
			.setTitle(productDTO.getTitle())
			.setDiscription(productDTO.getDescription())
			.setPrice(productDTO.getPrice());

		Product product = productBuilder.build();

		// Upload image
		if (!Objects.isNull(productDTO.getFileUploads()) &&
			!productDTO.getFileUploads().isEmpty()
		) {
			for (FileUpload fileUpload : productDTO.getFileUploads()) {
				ImageBuilder imageBuilder = ImageBuilder.builder()
					.setProductId(product.getId())
					.setFilename(fileUpload.fileName())
					.setSize(fileUpload.size())
					.setContentType(fileUpload.contentType());
				Image image = imageBuilder.build();

				product.addImage(image);

				String extension = FilenameUtils.getExtension(image.getFilename());

				String saveIn = String.format(
					WEB_ROOT + STATIC_IMAGE,
					product.getId(),
					image.getId(),
					extension);

				File finalFile = new File(saveIn);

				File tempFile = new File(fileUpload.uploadedFileName());
				try {
					FileUtils.createParentDirectories(finalFile);
					FileUtils.copyFile(tempFile, finalFile, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}

		// Cache
		productCache.put(product.getId(), product);

		Event evt = EventBuilder.builder()
			.setBody(product)
			.setType(Event.TYPE.CREATE)
			.build();
		DomainEvents.Product.raise(evt);
		return product;
	}
}
