
package vn.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.data.UploadsFiles;

@Table(name = "pages")
@Entity
@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
public class Pages implements UploadsFiles, Serializable {

	private static final long serialVersionUID = 4641853311314844969L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    private Integer id;
	
	@Column(name="cate")
	private Integer cate;
	
	@NotEmpty(message = "Tên bài không được chỉ có khoảng trắng")
	@Column(name="name")
	private String name;
	
	@Column(name="title")
	private String title;
	
	@Column(name="image")
	private String image;
	
	@Column(name="`desc`")
	private String desc;
	
	@Column(name="content")
	private String content;
	
	@Column(name="`status`")
	private String status;
	
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;
	
	@Transient @JsonIgnore
	private MultipartFile imgPresedent;

	public static String UPLOAD_PATH =  "/uploads/page/";
	
	@Override
	public String folderUpload() {
  		return UPLOAD_PATH;
	}

	@Override
	public List<String> filePass() {
		return Arrays.asList("png", "jpg");
	}
}
