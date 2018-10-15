package com.dtrade.utils;
import lombok.Data;

@Data
public class MyPair<L, R>  {

    /** Left object */
    public L first;
    /** Right object */
    public R second;

}
