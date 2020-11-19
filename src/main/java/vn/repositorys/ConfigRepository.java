package vn.repositorys;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import vn.model.Config;

public interface ConfigRepository extends CrudRepository<Config, Integer> {
	//Select * from config where key = "${key}"
	List<Config> findByKey(String key);
	//Select * from config where key = "${key}" and value = ${value}
	List<Config> findByKeyAndValue(String key, String value);
}