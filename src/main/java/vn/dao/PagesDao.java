package vn.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import vn.model.Pages;

@Repository("pagesDao")
@Transactional
public class PagesDao {
	
	private Logger log = LoggerFactory.getLogger(PagesDao.class);
	@PersistenceContext
	private EntityManager entityManager;
	
	public Pages findById(int id) {
		log.info("id: {}", id);
		try {
			return entityManager.find(Pages.class, id);
		} catch (RuntimeException e) {
			log.error("loi roi: {}", e.getMessage());
			return null;
		}
	}
	
	public void persist(Pages page) {
		try {
			entityManager.persist(page);
		} catch (RuntimeException e) {
			log.error("loi persistb roi: {}", e.getMessage());
			throw e;
		}
	}
	
	public void remove(Pages page) {
		try {
			entityManager.remove(page);
		} catch (RuntimeException e) {
			log.error("loi remove roi: {}", e.getMessage());
			throw e;
		}
	}
	
	public Pages merge(Pages page) {
		try {
			entityManager.merge(page);
			return page;
		} catch (RuntimeException e) {
			log.error("loi merge roi: {}", e.getMessage());
			throw e;
		}
	}
	
	public List<Pages> filter(Pages filter) {
		List<Pages> listItems = new ArrayList<Pages>();
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Pages> criteria = builder.createQuery(Pages.class);
			Root<Pages> root = criteria.from(Pages.class);
			//where name="${name}";
			if(filter.getName() != null) {
				criteria.where(
					builder.equal(root.get("name"), filter.getName())
				);
			}
			if(filter.getTitle() != null) {
				criteria.where(
					builder.equal(root.get("titie"), filter.getTitle())
				);
			}
			// where name="${name}" and title = "${title}";
			listItems = entityManager.createQuery(criteria).getResultList();
		}catch (RuntimeException ex) {
			log.error("loi tim theo ten: {}", ex.getMessage());
		}
		return listItems;
	}
}
