package vn.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.dao.CustomerOrderDao;
import vn.dao.CustomerOrderDetailDao;
import vn.data.CardInfo;
import vn.data.Customer;
import vn.model.CustomerOrder;
import vn.model.CustomerOrderDetail;

@Service
@Transactional
public class CreateOrderService {
	
	@Autowired private CustomerOrderDao customerOrderDao;
	@Autowired private CustomerOrderDetailDao customerOrderDetailDao;
	
	public boolean createOrder(Customer customer, List<CardInfo> items) {
		try {
			var customerOrder = CustomerOrder.builder()
					.phone(customer.getPhone())
					.email(customer.getEmail())
					.customerName(customer.getCustomerName())
					.address(customer.getAddress()).build();
			customerOrderDao.create(customerOrder);
			
			int subTotal = 0;
			for(CardInfo cardInfo : items) {
				var customerOrderDetail = CustomerOrderDetail.builder()
					.productId(cardInfo.getProductId())
					.productName(cardInfo.getName())
					.orderId(customerOrder.getId())
					.price(cardInfo.getPrice())
					.quality(cardInfo.getQuality())
					.total(cardInfo.getPrice() * cardInfo.getQuality()).build();
				subTotal +=  customerOrderDetail.getTotal();
				customerOrderDetailDao.create(customerOrderDetail);
			};
			customerOrder.setSubtotal(subTotal);
			customerOrder.setShippingCost(10000);
			customerOrder.setTotal(customerOrder.getSubtotal() + customerOrder.getShippingCost());
			
			customerOrderDao.update(customerOrder);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
