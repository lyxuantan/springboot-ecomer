package vn.data;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.validator.PhoneNumberContraint;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Tên khách hàng không được để trống")
	private String customerName;
	
	@PhoneNumberContraint(message="Sai số điện thoại rồi .!")
	private String phone;
	@NotEmpty(message = "Email không được để trống")
	private String email;
	@NotEmpty(message = "Địa chỉ không được để trống")
	private String address;
}