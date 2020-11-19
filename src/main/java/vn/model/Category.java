
package vn.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "category")
@Entity
@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
public class Category implements Serializable {

	private static final long serialVersionUID = 4641853311314844969L;
	public static Integer STATUS_ACTICE = 1;
	public static Integer STATUS_DEACTICE = 0;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id", unique = true, nullable = false)
    private Integer id;
	
	@Column(name="parent_id")
	private Integer parentId;
	
	@NotEmpty(message="Tên bài viết không được để trống")
	@Column(name="name")
	private String name;
	
	@Column(name="title")
	private String title;
	
	@Column(name="`desc`")
	private String desc;
	
	@Column(name="content")
	private String content;
	
	@Column(name="icon")
	private String icon;
	
	@Column(name="`status`")
	private Integer status;
	
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="create_time")
	private Date createTime;
	
	public String statusLable() {
		return mapStatus().get(status);
	}
	
	public Map<Integer, String> mapStatus() {
		var maps = new HashMap<Integer, String>();
		maps.put(STATUS_ACTICE, "Kích hoạt");
		maps.put(STATUS_DEACTICE, "Ngưng");
		return maps;
	}
}
