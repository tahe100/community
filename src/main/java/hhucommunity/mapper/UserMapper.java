package hhucommunity.mapper;

import hhucommunity.model.HhuUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    //hhu_user 表中id是自增的所以不用插入
    //前面的是数据库的字段名 后面是对象的属性名
    @Insert("INSERT INTO user (name,account_id,token,GMT_CREATE,gmt_modified) VALUES (#{name},#{accountId},#{token},#{gmtCreat},#{gmtModified})")
    void insert(HhuUser user);

    @Select("SELECT * from user where token = #{token}")//insert里的参数user是一个类直接放而findByToken里token不是类所以要加一个@param()
    HhuUser findByToken(@Param("token") String token);
}
