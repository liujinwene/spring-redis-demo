package com.example.order.service;

import java.util.List;

import com.example.order.cmd.CreateOrderCmd;
import com.example.order.cmd.ListOrderByCdCmd;
import com.example.order.cmd.ListOrderCmd;
import com.example.order.dto.OrderDetailDTO;
import com.example.order.resp.OrderResp;

public interface OrderService {
	void createOrder(CreateOrderCmd cmd);
	List<OrderDetailDTO> listOrder(ListOrderCmd cmd);
	void clearListOrder(ListOrderCmd cmd);
	
	List<OrderResp> listByCd(ListOrderByCdCmd cmd);
}
