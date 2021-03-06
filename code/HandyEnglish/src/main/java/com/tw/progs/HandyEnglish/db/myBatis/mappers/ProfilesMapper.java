package com.tw.progs.HandyEnglish.db.myBatis.mappers;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Profile;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by VCLERK on 22.03.2017.
 */
@Mapper
public interface ProfilesMapper {

    @Insert("insert into profiles(name, pass_hash) values(#{name}, #{pass_hash})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void insertUser(Profile profile);

    @Select("select * from profiles WHERE name = #{name}")
    Profile findUserByeMail(@Param("name")String name);

    @Select("select * from profiles")
    List<Profile> findAllUsers();
}
