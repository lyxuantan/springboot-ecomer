package vn.data;
import java.util.Optional;
import lombok.Data;

@Data
public class RObject<T> {
	
	public int errorCode = 200;
	public String message;
	public Object data;
	
	// new RObject(ObjectReturn)
	public RObject(T object) {
		data = object;
	}
	
	// RObject.response(Object data)
	public static RObject<Object> response(Object data){
		return new RObject<Object>(data);
	}
	
	// RObject.response("Okie")
	public static RObject<Object> response(String str){
		return new RObject<Object>(str);
	}
	
	// new RObject(int errorCode, String message)
	public RObject(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public String getMessage() {
		return Optional.ofNullable(message)
			.orElse("Sussess");
	}
}
