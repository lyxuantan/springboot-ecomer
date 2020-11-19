
package vn.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.aop.AmazonUtil;

@Table(name = "product_image")
@Entity
@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
@Builder
public class ProductImage implements Serializable {

	private static final long serialVersionUID = 4641853311314844969L;

	public static final int HINH_DAI_DIEN = 1;
	public static final int NOT_HINH_DAI_DIEN = 0;
	
	public static final int HINH_SLIDER = 1;
	public static final int NOT_HINH_SLIDER = 0;
	
	public void revertSlider() {
		this.isSlider = NOT_HINH_SLIDER;
	}
	
	public void revertPresident() {
		this.isPresident = NOT_HINH_DAI_DIEN;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id", unique = true, nullable = false)
    private Integer id;
	
	@Column(name = "file")
	private String file;
	
	@NotNull(message="Id Sản phẩm không được để trống")
	@Column(name = "product_id")
	private Integer productId;
	
	@Column(name = "is_president")
	private Integer isPresident;
	
	@Column(name = "is_slider")
	private Integer isSlider;
	
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="create_time")
	private Date createTime;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", insertable=false, updatable=false)
	@JsonIgnore
	private Product product;
	
	public void createImgUrl() {
		file = AmazonUtil.Util.host + Product.UPLOAD_PATH + file;
	}
}
