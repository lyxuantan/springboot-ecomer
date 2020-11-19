package vn.dao;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * Hàm count cần phải gọi sau hàm list.
 * Example.
 * return EntityQuery.create(entityManager, ExampleEntity.class)
    .setFirstResult(firstResult)
    .setMaxResults(maxResults)
    .addDescendingOrderBy("creationDate")
    .stringEqualsTo("statusCode", searchParams.getStatusCode())
    .objectEqualsTo("type", searchParams.getType())
    .greaterThanOrEqualsTo("creationDate", searchParams.getStartDate())
    .lessThanOrEqualsTo("creationDate", searchParams.getEndDate());
    .list();
*/

public class EntityQuery<E> {

    private final EntityManager entityManager;
    private final Class<E> entityClass;
    private final CriteriaBuilder criteriaBuilder;
    private final CriteriaQuery<E> criteriaQuery;
    private final Root<E> root;
    private final List<Predicate> predicates = new ArrayList<>();
    private Integer firstResult;
    private Integer maxResults;
    private List<Order> orders = new ArrayList<>();

    private EntityQuery(EntityManager entityManager, Class<E> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery(entityClass);
        this.root = criteriaQuery.from(criteriaQuery.getResultType());
    }

    public static <T> EntityQuery<T> create(EntityManager entityManager, Class<T> entityClass) {
        return new EntityQuery<T>(entityManager, entityClass);
    }

    public List<E> list() {
        TypedQuery<E> typedQuery = prepareSelectTypedQuery();
        if (firstResult != null) {
            typedQuery.setFirstResult(firstResult);
        }
        if (maxResults != null) {
            typedQuery.setMaxResults(maxResults);
        }
        return typedQuery.getResultList();
    }

    @SuppressWarnings("unchecked")
	public E uniqueResult() {
    	Object result = null;
    	try {
    		TypedQuery<E> typedQuery = prepareSelectTypedQuery();
    		typedQuery.setMaxResults(1);
    	    result = typedQuery.getSingleResult();
    	} catch (NoResultException e) {}
        return (E) result;
    }

    public long count() {
        CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        countCriteriaQuery.select(criteriaBuilder.count(countCriteriaQuery.from(entityClass)));
        countCriteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<Long> typedQuery = entityManager.createQuery(countCriteriaQuery);
        return typedQuery.getSingleResult();
    }

    private TypedQuery<E> prepareSelectTypedQuery() {
        criteriaQuery.select(root);
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).orderBy(orders);
        return entityManager.createQuery(criteriaQuery);
    }

    public EntityQuery<E> innerJoinFetch(String attribute) {
        root.fetch(attribute, JoinType.INNER);
        return this;
    }

    public EntityQuery<E> addAscendingOrderBy(String path) {
        orders.add(criteriaBuilder.asc(toJpaPath(path)));
        return this;
    }

    public EntityQuery<E> addDescendingOrderBy(String path) {
        orders.add(criteriaBuilder.desc(toJpaPath(path)));
        return this;
    }

    public EntityQuery<E> setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
        return this;
    }

    public EntityQuery<E> setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
        return this;
    }

    public EntityQuery<E> objectEqualsTo(String path, Object value) {
        if (value != null) {
            addEqualPredicate(path, value);
        }
        return this;
    }

    public Optional<Predicate> objectEqualsToPredicate(String path, Object value) {
        if (value != null) {
            return Optional.of(equalPredicate(path, value));
        }
        return Optional.empty();
    }

    public EntityQuery<E> like(String path, String value) {
        if (StringUtils.isNotBlank(value)) {
            predicates.add(criteriaBuilder.like(toJpaPath(path), '%' + value + '%'));
        }
        return this;
    }

    @SuppressWarnings("unchecked")
	public EntityQuery<E> addInDisjunction(Optional<Predicate>... optionalPredicates) {
        List<Predicate> predicateList = Arrays.stream(optionalPredicates).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        if (predicateList.size() > 1) {
            predicates.add(criteriaBuilder.or(predicateList.toArray(new Predicate[predicateList.size()])));
        } else if (predicateList.size() == 1) {
            predicates.add(predicateList.get(0));
        }
        return this;
    }

    public EntityQuery<E> stringEqualsTo(String path, String value) {
        if (StringUtils.isNotBlank(value)) {
            addEqualPredicate(path, value);
        }
        return this;
    }
    
    public EntityQuery<E> booleanEqualsTo(String path, Boolean value){
    	if(value != null) {
    		addEqualPredicate(path, value);
    	}
    	return this;
    }
    
    public EntityQuery<E> enumEqualsTo(String path, Enum<?> value){
    	if(value != null) {
    		addEqualPredicate(path, value);
    	}
    	return this;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public EntityQuery<E> greaterThanOrEqualsTo(String path, Comparable comparable) {
        if (Objects.nonNull(comparable)) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(toJpaPath(path), comparable));
        }
        return this;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public EntityQuery<E> greaterThan(String path, Comparable comparable) {
        if (Objects.nonNull(comparable)) {
            predicates.add(criteriaBuilder.greaterThan(toJpaPath(path), comparable));
        }
        return this;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public EntityQuery<E> lessThanOrEqualsTo(String path, Comparable comparable) {
        if (Objects.nonNull(comparable)) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(toJpaPath(path), comparable));
        }
        return this;
    }

    public EntityQuery<E> between(String path, Date firstDate, Date secondDate) {
        if (Objects.nonNull(firstDate) && Objects.nonNull(secondDate)) {
            predicates.add(criteriaBuilder.between(toJpaPath(path), firstDate, secondDate));
        }
        return this;
    }

    @SuppressWarnings({ "rawtypes" })
    public EntityQuery<E> in(String path, Collection collection) {
        if (CollectionUtils.isNotEmpty(collection)) {
            predicates.add(criteriaBuilder.in(toJpaPath(path)).value(collection));
        }
        return this;
    }

    @SuppressWarnings({ "rawtypes" })
    public EntityQuery<E> notIn(String path, Collection collection) {
        if (CollectionUtils.isNotEmpty(collection)) {
            predicates.add(criteriaBuilder.in(toJpaPath(path)).value(collection).not());
        }
        return this;
    }
    
    public EntityQuery<E> integerEqualsTo(String path, Integer value) {
    	if(value != null) {
    		addEqualPredicate(path, value);
    	}
        return this;
    }
    
    private void addEqualPredicate(String path, Object value) {
        predicates.add(equalPredicate(path, value));
    }

    private Predicate equalPredicate(String path, Object value) {
        return criteriaBuilder.equal(toJpaPath(path), value);
    }

    private <T> Path<T> toJpaPath(String stringPath) {
        String[] pathParts = StringUtils.split(stringPath, '.');
        assert pathParts != null && pathParts.length > 0 : "Path cannot be empty";
        Path<T> jpaPath = null;
        for (String eachPathPart : pathParts) {
            if (jpaPath == null) {
                jpaPath = root.get(eachPathPart);
            } else {
                jpaPath = jpaPath.get(eachPathPart);
            }
        }
        return jpaPath;
    }
}