package com.cmc.y3group.ddd.domain.subdomain.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
	private String address;
	private String fullname;
	private String phone;
}
