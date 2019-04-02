package com.spring.netty.util.impl;

import com.spring.netty.exception.ProvidersNoFoundException;
import com.spring.netty.util.LoadBalance;

public class RandomBalance implements LoadBalance {
    @Override
    public String chooseIP(String serviceName) throws ProvidersNoFoundException {
        return null;
    }
}
