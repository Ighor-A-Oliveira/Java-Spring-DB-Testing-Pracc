package com.ighor.postgreSQL.mappers;

//Converts between DTO and Entity.

public interface Mapper<A,B> {
    //we are being generic here because we can autogenerate the implementation when we implement the interface
    B mapTo(A a);
    A mapfrom(B a);
}
