package com.selfboot.chandao.persist;

import com.selfboot.chandao.common.Pager;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 增删改查基础DAO
 * Created by hwj on 2018/10/23.
 */
public interface CrudDAO<T> extends RootDAO {

    Integer deleteByPrimaryKey(Long id);

    Integer deleteByCondition(@Param("entity") T condition);

    Integer deleteBatch(@Param("list") List<T> list);

    Integer deleteBatchViaMap(@Param("entityMap") Map<Object,Object> paramMap);

    Integer insert(T record);

    Integer insertBatch(@Param("list") List<T> list);

    Integer insertBatchViaMap(@Param("entityMap") Map<Object,Object> paramMap);

    Integer insertSelective(T record);

    Integer updateByPrimaryKeySelective(T record);

    Integer updateByPrimaryKey(T record);

    Integer updateBatch(@Param("list") List<T> list);

    Integer updateBatchViaMap(@Param("entityMap") Map<Object,Object> paramMap);

    T selectByPrimaryKey(Long id);

    List<T> selectListByCondition(@Param("entity") T record);

    List<T> selectListWithPage(@Param("entity") T condition, @Param("pager") Pager page);

    Long count(T record);

    Long selectMaxId();

}
