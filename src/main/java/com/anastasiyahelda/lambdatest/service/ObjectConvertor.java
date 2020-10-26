package com.anastasiyahelda.lambdatest.service;

import com.anastasiyahelda.lambdatest.exception.ObjectConvertorException;

public interface ObjectConvertor {
    <T> T convertStringToObject(String xml, Class<T> clazz)throws ObjectConvertorException;
}
