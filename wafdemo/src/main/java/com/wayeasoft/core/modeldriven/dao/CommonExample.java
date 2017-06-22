package com.wayeasoft.core.modeldriven.dao;

import java.util.ArrayList;
import java.util.List;

public class CommonExample
{
        protected String orderByClause;

        protected boolean distinct;

        protected List<Criteria> oredCriteria;

        public CommonExample()
        {
                oredCriteria = new ArrayList<Criteria>();
        }

        public CommonExample orderBy(String orderByClause)
        {
                this.orderByClause = orderByClause;
                return this;
        }

        public String getOrderByClause()
        {
                return orderByClause;
        }

        public CommonExample distinct(boolean distinct)
        {
                this.distinct = distinct;
                return this;
        }

        public boolean isDistinct()
        {
                return distinct;
        }

        public List<Criteria> getOredCriteria()
        {
                return oredCriteria;
        }

        public void or(Criteria criteria)
        {
                oredCriteria.add(criteria);
        }

        public Criteria or()
        {
                Criteria criteria = createCriteriaInternal();
                oredCriteria.add(criteria);
                return criteria;
        }

        public Criteria createCriteria()
        {
                Criteria criteria = createCriteriaInternal();
                if (oredCriteria.size() == 0)
                {
                        oredCriteria.add(criteria);
                }
                return criteria;
        }

        protected Criteria createCriteriaInternal()
        {
                Criteria criteria = new Criteria(this);
                return criteria;
        }

        public void clear()
        {
                oredCriteria.clear();
                orderByClause = null;
                distinct = false;
        }

        /**
         * 表示整个where语句，由若干个criterion组成
         * @author niyuzhe
         *
         */
        protected abstract static class GeneratedCriteria
        {
                protected List<Criterion> criterions;

                protected GeneratedCriteria()
                {
                        super();
                        criterions = new ArrayList<Criterion>();
                }

                public boolean isValid()
                {
                        return criterions.size() > 0;
                }

                public List<Criterion> getAllCriteria()
                {
                        return criterions;
                }

                public List<Criterion> getCriteria()
                {
                        return criterions;
                }

                
                /**
                 * 自动生成的代码用的都是这个函数，直接在外层生成好所有内容的condition，如NAME = ？，再传入拼接
                 * @param condition
                 */
                protected void addCriterion(String condition)
                {
                        if (condition == null)
                        {
                                throw new RuntimeException("Value for condition cannot be null");
                        }
                        criterions.add(new Criterion(condition));
                }

                protected void addCriterion(String condition, Object value, String property)
                {
                        if (value == null)
                        {
                                throw new RuntimeException("Value for " + property + " cannot be null");
                        }
                        criterions.add(new Criterion(condition, value));
                }

                protected void addCriterion(String condition, Object value1, Object value2, String property)
                {
                        if (value1 == null || value2 == null)
                        {
                                throw new RuntimeException("Between values for " + property + " cannot be null");
                        }
                        criterions.add(new Criterion(condition, value1, value2));
                }

                public Criteria andIdIsNull()
                {
                        addCriterion("ID is null");
                        return (Criteria) this;
                }

                public Criteria andIdIsNotNull()
                {
                        addCriterion("ID is not null");
                        return (Criteria) this;
                }

                public Criteria andIdEqualTo(Long value)
                {
                        addCriterion("ID =", value, "id");
                        return (Criteria) this;
                }

                public Criteria andIdNotEqualTo(Long value)
                {
                        addCriterion("ID <>", value, "id");
                        return (Criteria) this;
                }

                public Criteria andIdGreaterThan(Long value)
                {
                        addCriterion("ID >", value, "id");
                        return (Criteria) this;
                }

                public Criteria andIdGreaterThanOrEqualTo(Long value)
                {
                        addCriterion("ID >=", value, "id");
                        return (Criteria) this;
                }

                public Criteria andIdLessThan(Long value)
                {
                        addCriterion("ID <", value, "id");
                        return (Criteria) this;
                }

                public Criteria andIdLessThanOrEqualTo(Long value)
                {
                        addCriterion("ID <=", value, "id");
                        return (Criteria) this;
                }

                public Criteria andIdIn(List<Long> values)
                {
                        addCriterion("ID in", values, "id");
                        return (Criteria) this;
                }

                public Criteria andIdNotIn(List<Long> values)
                {
                        addCriterion("ID not in", values, "id");
                        return (Criteria) this;
                }

                public Criteria andIdBetween(Long value1, Long value2)
                {
                        addCriterion("ID between", value1, value2, "id");
                        return (Criteria) this;
                }

                public Criteria andIdNotBetween(Long value1, Long value2)
                {
                        addCriterion("ID not between", value1, value2, "id");
                        return (Criteria) this;
                }

                public Criteria andNameIsNull()
                {
                        addCriterion("NAME is null");
                        return (Criteria) this;
                }

                public Criteria andNameIsNotNull()
                {
                        addCriterion("NAME is not null");
                        return (Criteria) this;
                }

                public Criteria andNameEqualTo(String value)
                {
                        addCriterion("NAME =", value, "name");
                        return (Criteria) this;
                }

                public Criteria andNameNotEqualTo(String value)
                {
                        addCriterion("NAME <>", value, "name");
                        return (Criteria) this;
                }

                public Criteria andNameGreaterThan(String value)
                {
                        addCriterion("NAME >", value, "name");
                        return (Criteria) this;
                }

                public Criteria andNameGreaterThanOrEqualTo(String value)
                {
                        addCriterion("NAME >=", value, "name");
                        return (Criteria) this;
                }

                public Criteria andNameLessThan(String value)
                {
                        addCriterion("NAME <", value, "name");
                        return (Criteria) this;
                }

                public Criteria andNameLessThanOrEqualTo(String value)
                {
                        addCriterion("NAME <=", value, "name");
                        return (Criteria) this;
                }

                public Criteria andNameLike(String value)
                {
                        addCriterion("NAME like", value, "name");
                        return (Criteria) this;
                }

                public Criteria andNameNotLike(String value)
                {
                        addCriterion("NAME not like", value, "name");
                        return (Criteria) this;
                }

                public Criteria andNameIn(List<String> values)
                {
                        addCriterion("NAME in", values, "name");
                        return (Criteria) this;
                }

                public Criteria andNameNotIn(List<String> values)
                {
                        addCriterion("NAME not in", values, "name");
                        return (Criteria) this;
                }

                public Criteria andNameBetween(String value1, String value2)
                {
                        addCriterion("NAME between", value1, value2, "name");
                        return (Criteria) this;
                }

                public Criteria andNameNotBetween(String value1, String value2)
                {
                        addCriterion("NAME not between", value1, value2, "name");
                        return (Criteria) this;
                }

                public Criteria andIsNull(String columnName)
                {
                        addCriterion(columnName + " is null");
                        return (Criteria) this;
                }

                public Criteria andIsNotNull(String columnName)
                {
                        addCriterion(columnName + " is not null");
                        return (Criteria) this;
                }

                public Criteria andEqualTo(String columnName, Object value)
                {
                        addCriterion(columnName + " =", value.toString(), columnName);
                        return (Criteria) this;
                }

                public Criteria andNotEqualTo(String columnName, Object value)
                {
                        addCriterion(columnName + " <>", value, columnName);
                        return (Criteria) this;
                }

                public Criteria andGreaterThan(String columnName, Object value)
                {
                        addCriterion(columnName + " >", value, columnName);
                        return (Criteria) this;
                }

                public Criteria andGreaterThanOrEqualTo(String columnName, Object value)
                {
                        addCriterion(columnName + " >=", value, columnName);
                        return (Criteria) this;
                }

                public Criteria andLessThan(String columnName, Object value)
                {
                        addCriterion(columnName + " <", value, columnName);
                        return (Criteria) this;
                }

                public Criteria andLessThanOrEqualTo(String columnName, Object value)
                {
                        addCriterion(columnName + " <=", value, columnName);
                        return (Criteria) this;
                }

                public Criteria andLike(String columnName, Object value)
                {
                        addCriterion(columnName + " like", value, columnName);
                        return (Criteria) this;
                }

                public Criteria andNotLike(String columnName, Object value)
                {
                        addCriterion(columnName + " not like", value, columnName);
                        return (Criteria) this;
                }

                public Criteria andIn(String columnName, List<Object> values)
                {
                        addCriterion(columnName + " in", values, columnName);
                        return (Criteria) this;
                }

                public Criteria andNotIn(String columnName, List<Object> values)
                {
                        addCriterion(columnName + " not in", values, columnName);
                        return (Criteria) this;
                }

                public Criteria andBetween(String columnName, Object value1, Object value2)
                {
                        addCriterion(columnName + " between", value1, value2, columnName);
                        return (Criteria) this;
                }

                public Criteria andNotBetween(String columnName, Object value1, Object value2)
                {
                        addCriterion(columnName + " not between", value1, value2, columnName);
                        return (Criteria) this;
                }
        }

        /**
         * This class was generated by MyBatis Generator. This class corresponds
         * to the database table MESSAGE
         *
         * @mbggenerated do_not_delete_during_merge Tue Jan 07 14:51:59 CST 2014
         */
        public static class Criteria extends GeneratedCriteria
        {

                private CommonExample exampleInstance;

                protected Criteria( CommonExample exampleInstance)
                {
                        super();
                        this.exampleInstance = exampleInstance;
                }
                
                public CommonExample finish()
                {
                        return exampleInstance;
                }
        }

        /**
         * 一条“条件查询语句”的“条件”，如name between a and b 
         */
        public static class Criterion
        {
                private String condition;

                private Object value;

                private Object secondValue;

                private boolean noValue;

                private boolean singleValue;

                private boolean betweenValue;

                private boolean listValue;

                private String typeHandler;

                public String getCondition()
                {
                        return condition;
                }

                public Object getValue()
                {
                        return value;
                }

                public Object getSecondValue()
                {
                        return secondValue;
                }

                public boolean isNoValue()
                {
                        return noValue;
                }

                public boolean isSingleValue()
                {
                        return singleValue;
                }

                public boolean isBetweenValue()
                {
                        return betweenValue;
                }

                public boolean isListValue()
                {
                        return listValue;
                }

                public String getTypeHandler()
                {
                        return typeHandler;
                }

                protected Criterion(String condition)
                {
                        super();
                        this.condition = condition;
                        this.typeHandler = null;
                        this.noValue = true;
                }

                protected Criterion(String condition, Object value, String typeHandler)
                {
                        super();
                        this.condition = condition;
                        this.value = value;
                        this.typeHandler = typeHandler;
                        if (value instanceof List<?>)
                        {
                                this.listValue = true;
                        } else
                        {
                                this.singleValue = true;
                        }
                }

                protected Criterion(String condition, Object value)
                {
                        this(condition, value, null);
                }

                protected Criterion(String condition, Object value, Object secondValue, String typeHandler)
                {
                        super();
                        this.condition = condition;
                        this.value = value;
                        this.secondValue = secondValue;
                        this.typeHandler = typeHandler;
                        this.betweenValue = true;
                }

                protected Criterion(String condition, Object value, Object secondValue)
                {
                        this(condition, value, secondValue, null);
                }
        }
}
