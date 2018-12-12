package com.error.undeclaredThrowableException;

import java.sql.SQLException;

public class ServiceImpl implements IService{
    @Override
    public void foo() throws SQLException {
        throw new NullPointerException("I test throw an checked Exception");
    }
}
