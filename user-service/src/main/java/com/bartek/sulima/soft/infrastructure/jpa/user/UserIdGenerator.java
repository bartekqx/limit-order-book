package com.bartek.sulima.soft.infrastructure.jpa.user;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.spi.Configurable;

import java.io.Serializable;
import java.util.Map;

public class UserIdGenerator implements IdentifierGenerator, Configurable {

    private static final String USER_ID_FORMAT = "USR.BS.%s.%s";

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        final String first = RandomStringUtils.randomAlphanumeric(10);
        final String second = RandomStringUtils.randomAlphanumeric(10);
        return String.format(USER_ID_FORMAT, first, second);
    }

    @Override
    public void configure(Map map) {

    }
}
