package vn.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CardInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private int productId;
	private int quality;
	private int price;
	private String image;
	private String name;
}