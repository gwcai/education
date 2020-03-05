package com.genius.coder.base.form;

import com.google.common.collect.Lists;
import groovy.lang.Tuple2;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;


/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
public class BaseForm<T extends Persistable<ID>, ID extends Serializable> {
    protected ID id;

    public BaseForm() {
    }

    public BaseForm<T, ID> toForm(T entity) {
        return this.toForm(true, entity, this);
    }

    private BaseForm<T, ID> toForm(boolean convert, T entity, BaseForm baseForm) {
        Assert.notNull(baseForm, "baseForm不能为空");
        this.setId(entity.getId());
        if (baseForm instanceof BaseAuditForm && entity instanceof BaseAuditable) {
            ((BaseAuditForm)baseForm).setCreatedBy((Serializable)((BaseAuditable)entity).getCreatedBy().orElse((Object)null));
            ((BaseAuditForm)baseForm).setLastModifiedBy((Serializable)((BaseAuditable)entity).getLastModifiedBy().orElse((Object)null));
            ((BaseAuditForm)baseForm).setCreatedDate((LocalDateTime)((BaseAuditable)entity).getCreatedDate().orElse((Object)null));
            ((BaseAuditForm)baseForm).setLastModifiedDate((LocalDateTime)((BaseAuditable)entity).getLastModifiedDate().orElse((Object)null));
            ((BaseAuditForm)baseForm).setCreatedUserName(((BaseAuditable)entity).getCreatedUserName());
        }

        Tuple2<String[], List<Field>> tuple2 = this.getIgnoreProperties(baseForm);
        BeanUtils.copyProperties(entity, baseForm, (String[])tuple2.getFirst());

        try {
            this.entityConvert(convert, entity, baseForm, tuple2);
        } catch (InstantiationException | IllegalAccessException var6) {
            var6.printStackTrace();
        }

        return this;
    }

    public T toEntity() {
        return this.toEntity(true, this);
    }

    private T toEntity(boolean convert, BaseForm baseForm) {
        Assert.notNull(baseForm, "baseForm不能为空");
        Class<T> tClass = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T entity = null;

        try {
            entity = tClass.newInstance();
            if (baseForm instanceof BaseAuditForm && entity instanceof BaseAuditable) {
                ((BaseAuditable)entity).setCreatedBy(((BaseAuditForm)baseForm).getCreatedBy());
                ((BaseAuditable)entity).setCreatedDate(((BaseAuditForm)baseForm).getCreatedDate());
                ((BaseAuditable)entity).setLastModifiedBy(((BaseAuditForm)baseForm).getLastModifiedBy());
                ((BaseAuditable)entity).setLastModifiedDate(((BaseAuditForm)baseForm).getLastModifiedDate());
                ((BaseAuditable)entity).setId(((BaseAuditForm)baseForm).getId());
            }

            Tuple2<String[], List<Field>> tuple2 = this.getIgnoreProperties(baseForm);
            BeanUtils.copyProperties(this, entity, (String[])tuple2.getFirst());
            this.formConvert(convert, baseForm, tClass, entity, tuple2);
        } catch (IllegalAccessException | InstantiationException var6) {
            var6.printStackTrace();
        }

        return entity;
    }

    /***
     * 将实体转为form对象
     * @param convert
     * @param baseDomain
     * @param baseForm
     * @param tuple2
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void entityConvert(boolean convert, Persistable baseDomain, BaseForm baseForm, Tuple2<String[], List<Field>> tuple2) throws IllegalAccessException, InstantiationException {
        if (convert) {
            Iterator itor = ((List)tuple2.getSecond()).iterator();

            while(true) {
                InnerForm innerForm;
                String fieldName;
                Class domainClass;
                Persistable[] baseDomains;
                label102:
                do {
                    while(itor.hasNext()) {
                        Field field = (Field)itor.next();
                        field.setAccessible(true);
                        innerForm = field.getAnnotation(InnerForm.class);
                        Class<?> type = field.getType();
                        fieldName = field.getName();
                        if (type.isArray()) {
                            baseDomains = (Persistable[]) field.get(baseDomain);
                            domainClass = type.getComponentType();
                            continue label102;
                        }

                        List<Field> allFields = this.getAllFields(baseDomain.getClass(), new ArrayList());
                        Iterator var12;
                        Field field1;
                        Collection baseDomains1;
                        Iterator var16;
                        Persistable baseDomain1;
                        BaseForm baseForm1;
                        if (type == List.class) {
                            domainClass = (Class)((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
                            var12 = allFields.iterator();

                            while(var12.hasNext()) {
                                field1 = (Field)var12.next();
                                if (fieldName.equals(field1.getName())) {
                                    field1.setAccessible(true);
                                    baseDomains1 = (Collection)field1.get(baseDomain);
                                    if (CollectionUtils.isEmpty(baseDomains1)) {
                                        break;
                                    }

                                    List<Object> objects = new ArrayList();
                                    var16 = baseDomains1.iterator();

                                    while(var16.hasNext()) {
                                        baseDomain1 = (Persistable)var16.next();
                                        baseForm1 = (BaseForm)domainClass.newInstance();
                                        objects.add(baseForm1.toForm(innerForm.required(), baseDomain1, baseForm1));
                                    }

                                    this.setValue(baseForm, fieldName, objects);
                                    break;
                                }
                            }
                        } else if (type != Set.class) {
                            if (BaseForm.class.isAssignableFrom(type)) {
                                //BaseForm baseForm1;
                                if (this.getClass().getSuperclass() == BaseAuditForm.class) {
                                    if (type == BaseForm.class) {
                                        baseForm1 = (BaseForm)((Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[2]).newInstance();
                                    } else {
                                        baseForm1 = (BaseForm)type.newInstance();
                                    }
                                } else {
                                    baseForm1 = (BaseForm)type.newInstance();
                                }

                                var12 = allFields.iterator();

                                while(var12.hasNext()) {
                                    field1 = (Field)var12.next();
                                    field1.setAccessible(true);
                                    if (fieldName.equals(field1.getName())) {
                                        Persistable entity = (Persistable)field1.get(baseDomain);
                                        if (entity != null) {
                                            Object value = baseForm1.toForm(innerForm.required(), entity, baseForm1);
                                            this.setValue(baseForm, fieldName, value);
                                            break;
                                        }
                                    }
                                }
                            }
                        } else {
                            domainClass = (Class)((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
                            var12 = allFields.iterator();

                            while(var12.hasNext()) {
                                field1 = (Field)var12.next();
                                if (fieldName.equals(field1.getName())) {
                                    field1.setAccessible(true);
                                    baseDomains1 = (Collection)field1.get(baseDomain);
                                    if (CollectionUtils.isEmpty(baseDomains1)) {
                                        break;
                                    }

                                    Set<Object> objects = new LinkedHashSet();
                                    var16 = baseDomains1.iterator();

                                    while(var16.hasNext()) {
                                        baseDomain1 = (Persistable)var16.next();
                                        baseForm1 = (BaseForm)domainClass.newInstance();
                                        objects.add(baseForm1.toForm(innerForm.required(), baseDomain1, baseForm1));
                                    }

                                    this.setValue(baseForm, fieldName, objects);
                                    break;
                                }
                            }
                        }
                    }

                    return;
                } while(!ArrayUtils.isNotEmpty(baseDomains));

                Object[] objects = new Object[baseDomains.length];

                for(int i = 0; i < baseDomains.length; ++i) {
                    BaseForm baseForm1 = (BaseForm)domainClass.newInstance();
                    objects[i] = baseForm1.toForm(innerForm.required(), baseDomains[i], baseForm1);
                }

                this.setValue(baseForm, fieldName, objects);
            }
        }
    }


    private List<Field> getAllFields(Class t, List<Field> fields) {
        if (Persistable.class.isAssignableFrom(t)) {
            fields.addAll(Lists.newArrayList(t.getDeclaredFields()));
            if (Object.class != t.getSuperclass()) {
                return this.getAllFields(t.getSuperclass(), fields);
            }
        }

        if (BaseForm.class.isAssignableFrom(t) && t != BaseForm.class) {
            fields.addAll(Lists.newArrayList(t.getDeclaredFields()));
            return this.getAllFields(t.getSuperclass(), fields);
        } else {
            return fields;
        }
    }

    private void setValue(BaseForm baseForm, String fieldName, Object value) throws IllegalAccessException {
        List<Field> allFields = this.getAllFields(baseForm.getClass(), new ArrayList());
        Iterator var5 = allFields.iterator();

        Field field1;
        do {
            if (!var5.hasNext()) {
                return;
            }

            field1 = (Field)var5.next();
        } while(!fieldName.equals(field1.getName()));

        field1.setAccessible(true);
        field1.set(baseForm, value);
    }

    private void formConvert(boolean convert, BaseForm baseForm, Class<T> tClass, T entity, Tuple2<String[], List<Field>> tuple2) throws IllegalAccessException {
        if (convert) {
            Iterator itor = ((List)tuple2.getSecond()).iterator();

            while(true) {
                Field field;
                InnerForm innerForm;
                String fieldName;
                BaseForm[] baseForms;
                label65:
                do {
                    while(itor.hasNext()) {
                        field = (Field)itor.next();
                        Class<?> type = field.getType();
                        innerForm = field.getAnnotation(InnerForm.class);
                        if (type.isArray()) {
                            baseForms = (BaseForm[])field.get(baseForm);
                            continue label65;
                        }

                        Iterator var13;
                        BaseForm baseForm1;
                        if (type == List.class) {
                            List<BaseForm> baseForms1 = (List)field.get(baseForm);
                            if (!CollectionUtils.isEmpty(baseForms1)) {
                                fieldName = field.getName();
                                List<Object> objects = new ArrayList();
                                var13 = baseForms1.iterator();

                                while(var13.hasNext()) {
                                    baseForm1 = (BaseForm)var13.next();
                                    objects.add(baseForm1.toEntity(innerForm.required(), baseForm1));
                                }

                                this.setValue(tClass, entity, fieldName, objects);
                            }
                        } else if (type != Set.class) {
                            if (BaseForm.class.isAssignableFrom(type.getSuperclass())) {
                                BaseForm form = (BaseForm)field.get(baseForm);
                                if (form != null) {
                                    fieldName = field.getName();
                                    Object value = form.toEntity(innerForm.required(), form);
                                    this.setValue(tClass, entity, fieldName, value);
                                }
                            }
                        } else {
                            Set<BaseForm> baseForms1 = (Set)field.get(baseForm);
                            if (!CollectionUtils.isEmpty(baseForms1)) {
                                fieldName = field.getName();
                                Set<Object> objects = new LinkedHashSet();
                                var13 = baseForms1.iterator();

                                while(var13.hasNext()) {
                                    baseForm1 = (BaseForm)var13.next();
                                    objects.add(baseForm1.toEntity(innerForm.required(), baseForm1));
                                }

                                this.setValue(tClass, entity, fieldName, objects);
                            }
                        }
                    }

                    return;
                } while(!ArrayUtils.isNotEmpty(baseForms));

                fieldName = field.getName();
                Object[] objects = new Object[baseForms.length];

                for(int i = 0; i < baseForms.length; ++i) {
                    objects[i] = baseForms[i].toEntity(innerForm.required(), baseForms[i]);
                }

                this.setValue(tClass, entity, fieldName, objects);
            }
        }
    }

    private void setValue(Class<T> tClass, T entity, String fieldName, Object value) throws IllegalAccessException {
        Field[] fields = tClass.getDeclaredFields();
        int len = fields.length;

        for(int i = 0; i < len; ++i) {
            Field field1 = fields[i];
            if (fieldName.equals(field1.getName())) {
                field1.setAccessible(true);
                field1.set(entity, value);
                return;
            }
        }

    }

    public Tuple2<String[], List<Field>> getIgnoreProperties(BaseForm form) {
        ArrayList<String> ignoreProperties = new ArrayList();
        ArrayList<Field> formProperties = new ArrayList();
        List<Field> allFields = this.getAllFields(form.getClass(), new ArrayList());
        Iterator itor = allFields.iterator();

        while(itor.hasNext()) {
            Field field = (Field)itor.next();
            if (field.getAnnotation(WrapIgnore.class) != null) {
                ignoreProperties.add(field.getName());
            }

            if (field.getAnnotation(InnerForm.class) != null) {
                ignoreProperties.add(field.getName());
                field.setAccessible(true);
                formProperties.add(field);
            }
        }

        String[] strings = new String[ignoreProperties.size()];
        ignoreProperties.toArray(strings);
        return new Tuple2(strings, formProperties);
    }

    public ID getId() {
        return this.id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}