package com.tw.progs.HandyEnglish.models.daos;

/**
 * Created by VCLERK on 20.04.2017.
 */
public class NullDAO implements IDAO{
    @Override
    public Integer getId() {
        return -1;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getPass_hash() {
        return "";
    }
}
