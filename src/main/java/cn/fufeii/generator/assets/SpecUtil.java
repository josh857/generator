package cn.fufeii.generator.assets;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 快速构建spring-data-jpa的复杂查询对象 {@link Specification}
 *
 * @author FuFei
 */
public final class SpecUtil {

    private SpecUtil() {
    }

    public static <T> Builder<T> and() {
        return new Builder<>(Predicate.BooleanOperator.AND);
    }

    public static <T> Builder<T> or() {
        return new Builder<>(Predicate.BooleanOperator.OR);
    }

    public static final class Builder<T> {

        private final Predicate.BooleanOperator operator;
        private final List<Specification<T>> specificationList;

        public Builder(Predicate.BooleanOperator operator) {
            this.operator = operator;
            this.specificationList = new ArrayList<>();
        }

        public Builder<T> equal(String field, Object value) {
            return this.equal(true, field, value);
        }

        public Builder<T> equal(boolean condition, String field, Object value) {
            return this.addSpecification(condition, (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.equal(root.get(field), value));
        }

        public Builder<T> notEqual(String field, Object value) {
            return this.notEqual(true, field, value);
        }

        public Builder<T> notEqual(boolean condition, String field, Object value) {
            return this.addSpecification(condition, (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.notEqual(root.get(field), value));
        }

        public Builder<T> like(String field, String value) {
            return this.like(true, field, value);
        }

        public Builder<T> like(boolean condition, String field, String value) {
            return this.addSpecification(condition, (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.like(root.get(field), value));
        }

        public Builder<T> in(String field, Collection<?> values) {
            return this.in(true, field, values);
        }

        public Builder<T> in(boolean condition, String field, Collection<?> values) {
            return this.addSpecification(condition, (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> root.get(field).in(values));
        }

        public Builder<T> notIn(String field, Collection<?> values) {
            return this.notIn(true, field, values);
        }

        public Builder<T> notIn(boolean condition, String field, Collection<?> values) {
            return this.addSpecification(condition, (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> root.get(field).in(values).not());
        }

        public <Y extends Comparable<Y>> Builder<T> between(String field, Y start, Y end) {
            return this.between(true, field, start, end);
        }

        public <Y extends Comparable<Y>> Builder<T> between(boolean condition, String field, Y start, Y end) {
            return this.addSpecification(condition, (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.between(root.get(field), start, end));
        }

        public <Y extends Comparable<Y>> Builder<T> gt(String field, Y value) {
            return this.gt(true, field, value);
        }

        public <Y extends Comparable<Y>> Builder<T> gt(boolean condition, String field, Y value) {
            return this.addSpecification(condition, (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.greaterThan(root.get(field), value));
        }

        public <Y extends Comparable<Y>> Builder<T> ge(String field, Y value) {
            return this.ge(true, field, value);
        }

        public <Y extends Comparable<Y>> Builder<T> ge(boolean condition, String field, Y value) {
            return this.addSpecification(condition, (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.greaterThanOrEqualTo(root.get(field), value));
        }

        public <Y extends Comparable<Y>> Builder<T> lt(String field, Y value) {
            return this.lt(true, field, value);
        }

        public <Y extends Comparable<Y>> Builder<T> lt(boolean condition, String field, Y value) {
            return this.addSpecification(condition, (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.lessThan(root.get(field), value));
        }

        public <Y extends Comparable<Y>> Builder<T> le(String field, Y value) {
            return this.le(true, field, value);
        }

        public <Y extends Comparable<Y>> Builder<T> le(boolean condition, String field, Y value) {
            return this.addSpecification(condition, (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.lessThanOrEqualTo(root.get(field), value));
        }

        public Builder<T> isNull(String field) {
            return this.isNull(true, field);
        }

        public Builder<T> isNull(boolean condition, String field) {
            return this.addSpecification(condition, (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.isNull(root.get(field)));
        }

        public Builder<T> isNotNull(String field) {
            return this.isNotNull(true, field);
        }

        public Builder<T> isNotNull(boolean condition, String field) {
            return this.addSpecification(condition, (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.isNotNull(root.get(field)));
        }

        public Builder<T> newSpecification(Specification<T> specification) {
            return this.newSpecification(true, specification);
        }

        public Builder<T> newSpecification(boolean condition, Specification<T> specification) {
            return this.addSpecification(condition, specification);
        }

        private Builder<T> addSpecification(boolean condition, Specification<T> specification) {
            Assert.notNull(specification, "specification can not be null");
            if (condition) {
                this.specificationList.add(specification);
            }
            return this;
        }

        public Specification<T> build() {
            return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
                Predicate[] predicates = specificationList.stream().map(s -> s.toPredicate(root, query, cb)).toArray(Predicate[]::new);
                return Predicate.BooleanOperator.AND.equals(operator) ? cb.and(predicates) : cb.or(predicates);
            };
        }

    }

}
