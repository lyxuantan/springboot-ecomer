package vn.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
public class ProductJionImage implements Serializable {
	private static final long serialVersionUID = 7048428705613080544L;
	private String name;
	private String title;
	private String file;
}
