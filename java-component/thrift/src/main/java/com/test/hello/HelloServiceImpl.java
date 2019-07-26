package com.test.hello;

public class HelloServiceImpl implements Hello.Iface {
    @Override
    public String helloString(String para) throws org.apache.thrift.TException {
        return para;
    }
}
