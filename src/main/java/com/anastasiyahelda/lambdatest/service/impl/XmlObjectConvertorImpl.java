package com.anastasiyahelda.lambdatest.service.impl;

import com.anastasiyahelda.lambdatest.exception.ObjectConvertorException;
import com.anastasiyahelda.lambdatest.service.ObjectConvertor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;

public class XmlObjectConvertorImpl implements ObjectConvertor {

    @Override
    public <T> T convertStringToObject(String xml, Class<T> clazz)throws ObjectConvertorException {
        T obj;
        try {
            obj =  (T) JAXBContext.newInstance(clazz)
                    .createUnmarshaller()
                    .unmarshal(new ByteArrayInputStream(xml.getBytes()));
        } catch (JAXBException e) {
            throw new ObjectConvertorException("An error occurred during converting xml to object", e);
        }

        return obj;
    }
}