package com.dtrade.utils;
import lombok.Data;

@Data
public class MyPair<L, R>  {

    /** Left object */
    public L first;
    /** Right object */
    public R second;

    public MyPair(L l, R r){
        this.first = l;
        this.second = r;
    }

}
