
package vn.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.validator.PhoneNumberContraint;

@Table(name = "customer_order")
@Entity
@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
@Builder
public class CustomerOrder implements Serializable {

	private static final long serialVersionUID = 4641853311314844969L;
	public static Integer STATUS_ACTICE = 1;
	public static Integer STATUS_DEACTICE = 0;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id", unique = true, nullable = false)
    private Integer id;
	
	@Column(name="customer_id")
	private Integer customerId;
	
	@NotEmpty(message="Tên khách hàng không được để trống")
	@Column(name="customer_name")
	private String customerName;
	
	@PhoneNumberContraint(message="Sai số điện thoại rồi .!")
	@Column(name="phone")
	private String phone;
	
	@Column(name="email")
	private String email;
	
	@Column(name="address")
	private String address;
	
	@Column(name="subtotal")
	private Integer subtotal;
	
	@Column(name="shipping_cost")
	private Integer shippingCost;
	
	@Column(name="`status`")
	private Integer status;
	
	@Column(name="cod")
	private Integer cod;
	
	@Column(name="total")
	private Integer total;
	
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="create_time")
	private Date createTime;
}
