package com.dtrade.utils;
import lombok.Data;

@Data
public class MyPair<L, R>  {

    /** Left object */
    public L left;
    /** Right object */
    public R right;

}
