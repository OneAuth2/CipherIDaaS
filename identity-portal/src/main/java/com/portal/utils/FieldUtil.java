/*******************************************************************************
 * CopyRight (c) 2005-2011 TAOTAOSOU Co, Ltd. All rights reserved.
 * Filename:    FieldUtil.java
 * Creator:     wangqi
 * Create-Date: 2011-4-27 上午10:32:02
 *******************************************************************************/
package com.portal.utils;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * TODO
 * 
 * @author wangqi
 * @version $Id: FieldUtil.java 14 2012-01-10 11:54:14Z archie $
 */
public class FieldUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(FieldUtil.class);

	/**
	 * 返回字段的此类型实际类型参数
	 * 
	 * @param field
	 * @return
	 */
	static public Class<?> getComponentClass(Field field) {
		if (null == field) {
			String errmsg = "FieldUtils: field is null, can't get compoment class.";
			logger.error(errmsg);
			throw new RuntimeException(errmsg);
		}
		Type type = field.getGenericType();

		if (null == type || !(ParameterizedType.class.isInstance(type))) {
			String errmsg = "FieldUtils: getGenericType invalid, can't get compoment class."
					+ "/ cause field is [" + field + "]";
			logger.error(errmsg);
			throw new RuntimeException(errmsg);
		}
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Class<?> clazz = (Class<?>) parameterizedType.getActualTypeArguments()[0];
		return clazz;
	}

	static public Field[] getAllFieldsOfClass(Class<?> cls) {
		Field[] fields = new Field[0];

		Class<?> itr = cls;
		while ((null != itr) && !itr.equals(Object.class)) {
			fields = (Field[]) ArrayUtils.addAll(itr.getDeclaredFields(),
					fields);
			itr = itr.getSuperclass();
		}

		return fields;
	}

	static public Field[] getAnnotationFieldsOf(Class<?> cls,
			Class<? extends Annotation> annotationClass) {
		Field[] fields = new Field[0];

		Class<?> itr = cls;
		while ((null != itr) && !itr.equals(Object.class)) {
			fields = (Field[]) ArrayUtils.addAll(itr.getDeclaredFields(),
					fields);
			itr = itr.getSuperclass();
		}

		int idx = 0;
		for (Field field : fields) {
			if (null != field.getAnnotation(annotationClass)) {
				idx++;
			}
		}

		Field[] ret = new Field[idx];
		idx = 0;
		for (Field field : fields) {
			field.setAccessible(true);
			if (null != field.getAnnotation(annotationClass)) {
				ret[idx++] = field;
			}
		}

		return ret;
	}

	static public Object getFieldValue(Object object, String fieldName) {
		Class<?> claz = object.getClass();

		try {
			
			Field field = claz.getDeclaredField(fieldName);
			field.setAccessible(true);

			return field.get(object);
		} catch (IllegalArgumentException e) {
			logger.error("getFieldValue:", e);
		} catch (IllegalAccessException e) {
			logger.error("getFieldValue:", e);
		} catch (SecurityException e) {
			logger.error("getFieldValue:", e);
		} catch (NoSuchFieldException e) {
			logger.error("getFieldValue:", e);
		}

		return null;
	}

	static public void setFieldValue(Object object, String fieldName,
			Object fieldValue) {
		Class<?> claz = object.getClass();

		try {
			Field field = claz.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(object, fieldValue);
		} catch (SecurityException e) {
			logger.error("setFieldValue:", e);
		} catch (NoSuchFieldException e) {
			logger.error("setFieldValue:", e);
		} catch (IllegalArgumentException e) {
			logger.error("setFieldValue:", e);
		} catch (IllegalAccessException e) {
			logger.error("setFieldValue:", e);
		}

	}
}
