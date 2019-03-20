package com.selfboot.chandao.persist;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hwj on 2019/3/20.
 */
public class BootStrapServiceImpl<T extends BaseEntity,D extends CrudDAO<T>> extends CrudService<T,D>
        implements BootStrapService<T> {

    @Override
    public Map<String, Object> selectRecord(T t, Integer offset, Integer limit) {
        // 准备结果集
        Map<String, Object> resultSet = new HashMap<>();
        long total = 0;
        boolean isPagination = true;

        offset = (offset == null ? 0 : offset);
        limit = (limit == null ? 10 : limit);

        // 检查是否需要分页
        if (offset < 0 && limit < 0)
            isPagination = false;

        if (isPagination) {
            PageHelper.offsetPage(offset, limit);
        }

        List<T> userRecordDOS = (List<T>) super.queryList(t).getResult();

        total = isPagination ? new PageInfo<>(userRecordDOS).getTotal() :
                CollectionUtils.isEmpty(userRecordDOS) ? 0 : userRecordDOS.size();

        resultSet.put("data", userRecordDOS);
        resultSet.put("total", total);
        return resultSet;
    }
}
