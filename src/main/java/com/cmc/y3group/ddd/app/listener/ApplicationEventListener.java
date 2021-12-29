package com.cmc.y3group.ddd.app.listener;

import com.cmc.y3group.ddd.config.VertxConfiguration;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import com.cmc.y3group.ddd.domain.subdomain.product.repositories.ProductRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationEventListener {
	@Autowired
	private List<AbstractVerticle> verticles;

	@Autowired
	private ProductRepository productRpository;

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReadyEvent() {
		VertxConfiguration vertxConfiguration = VertxConfiguration.getInstance();
		Vertx vertx = vertxConfiguration.getVertx();
		verticles.parallelStream().forEach(vertx::deployVerticle);
	}


	@EventListener(ApplicationReadyEvent.class)
	public void databaseDemo() {

		List<Product> products = productRpository.findAll();
		if (!products.isEmpty())
			return;

		products = new ArrayList<>();
		Product pDemo1 = new Product();
		pDemo1.setName("Giày thể thao running nam Anta 812115557-4");
		pDemo1.setDiscription("Giày thể thao running nam Anta 812115557-4 sở hữu thiết kế thời trang, năng động, cùng chất liệu bền bỉ hỗ trợ vận động tốt và bảo vệ đôi chân cho người mang. Đế cao su mềm, êm ái giúp bạn cảm thấy dễ chịu khi di chuyển trong thời gian dài. Đồng hành cùng thiết kế thời trang, giày thể thao Anta mang tính năng thoáng khí, giúp cân bằng nhiệt và độ ẩm trong những điều kiện môi trường khác nhau, đế có các đường rãnh chống trơn trượt. Sản phẩm sở hữu phong cách hiện đại, khỏe khoắn, màu sắc trẻ trung hợp với nhiều lứa tuổi và dáng người. Đường may tỉ mỉ, tinh tế tạo cho bạn cảm giác yên tâm về chất lượng. Sản phẩm có tính ứng dụng cao, mang khi tập luyện thể thao, đi làm hay đi chơi");
		pDemo1.setPrice(859000.0);
		products.add(pDemo1);


		Product pDemo2 = new Product();
		pDemo2.setName("Giày chạy bộ nam Skechers GO RUN FAST - 220090");
		pDemo2.setDiscription("Thiết kế đẹp mắt, hợp thời trang, phù hợp mang với nhiều loại trang phục khác nhau. Chấ liệu: Tổng hợp");
		pDemo2.setPrice(1463000.0);
		products.add(pDemo2);


		Product pDemo3 = new Product();
		pDemo3.setName("Giày thể thao nam màu đen đế trắng");
		pDemo3.setDiscription("Kiểu giày thể thao thắt dây năng động\n" +
			"\n" +
			"Cổ giày có lót mút êm chân\n" +
			"\n" +
			"Giày được thiết kế đẹp mắt, có tính thẩm mỹ cao, nhiều người yêu thích\n" +
			"\n" +
			"Mũi giày ôm theo dáng bàn chân, không gây khó chịu trong lúc di chuyển");
		pDemo3.setPrice(659000.0);
		products.add(pDemo3);


		Product pDemo4 = new Product();
		pDemo4.setName("Giày thể thao nữ đế bằng cao 4cm mã SD01 mềm êm giá rẻ học sinh đi học đi chơi đơn giản màu đen trắng/xanh");
		pDemo4.setDiscription("TÊN SẢN PHẨM: SNEAKER MÃ SD BASIC 03\n" +
			"\n" +
			"Nếu bạn đang đi tìm 1 đôi sneaker vừa cá tính, vừa dễ mix đồ thì thực sự ẻm giày thể thao này của nhà UMI SHOES chắc chắn không thể bỏ qua lun\n" +
			"\n" +
			"Size: 36 - 40\n" +
			"\n" +
			"Màu sắc:\n" +
			"\n" +
			"2 Màu siêu trẻ trung, năng động mà cực dễ mix đồ: ĐEN TRẮNG VÀ XANH TRẮNG\n" +
			"\n" +
			"● Thông tin về sản phẩm Giày thể thao nữ ulzzang tăng chiều cao\n" +
			"\n" +
			"︎ Sneaker ulzzang Phần Upper da PU và miếng nubuck mềm tạo sự cực kì thoải mái khi mang\n" +
			"\n" +
			"︎ Sneaker ulzzang Phần Midsole EVA rất êm, lót thoát khí dạng tổ ong, chống mùi hôi khi mang cả ngày\n" +
			"\n" +
			"︎sneaker ulzzang Phần Outsole (đế ngoài) là điểm quyết định của em giày này.\n" +
			"\n" +
			"Sản phẩm thiết kế phần đế chắc chắn\n" +
			"\n" +
			"Đế cao su lưu hóa dẻo tạo độ êm ái thoải mái, êm chân suốt cả ngày mang\n" +
			"\n" +
			"● MIX & MATCH\n" +
			"\n" +
			"Giày thể thao nữ ulzzang này có thể sử dụng như giày thể thao nữ năng động đi chơi, giày thể thao nữ năng động đi tập, giày thể thao nữ năng động đi học, giày thể thao nữ năng động đi dạo phố");
		pDemo4.setPrice(459000.0);
		products.add(pDemo4);

		productRpository.saveAll(products);
	}
}
