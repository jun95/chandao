package com.selfboot.chandao.persist;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.selfboot.chandao.common.Pager;
import com.selfboot.chandao.common.ServiceResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by 87570 on 2019/3/16.
 */
public abstract class CrudService<V extends BaseEntity,D extends CrudDAO<V>> implements
        BaseService<V> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected D targetDAO;

    @Override
    public ServiceResult save(List<V> entity) {
        ServiceResult result = new ServiceResult();
        if (CollectionUtils.isEmpty(entity)) {
            result.addError("参数不能为空");
            return result;
        }

        boolean onlyOne = entity.size() == 1 ? true : false;
        V pojo = entity.get(0);
        if (onlyOne) {
            if (pojo.isUpdate()) {
                targetDAO.updateByPrimaryKeySelective(pojo);
            } else {
                targetDAO.insert(pojo);
            }
            result.setReturnMessage("单条操作成功");
        } else {
            if (pojo.isUpdate()) {
                targetDAO.updateBatch(entity);
            } else {
                targetDAO.insertBatch(entity);
            }
            result.setReturnMessage("批量操作成功");
        }

        return result;
    }

    @Override
    public ServiceResult delete(List<V> entity) {
        ServiceResult result = new ServiceResult();
        if (CollectionUtils.isEmpty(entity)) {
            result.addError("参数不能为空");
            return result;
        }
        if (entity.size() == 1) {
            targetDAO.deleteByCondition(entity.get(0));
        } else {
            targetDAO.deleteBatch(entity);
        }

        return result;
    }

    @Override
    public int delete(Long id) {
        return targetDAO.deleteByPrimaryKey(id);
    }

    @Override
    public ServiceResult queryOne(V entity) {
        ServiceResult result = new ServiceResult();
        if (entity == null) {
            result.addError("入参不能为空");
            return result;
        }
        List<V> vs = targetDAO.selectListByCondition(entity);
        boolean empty = CollectionUtils.isEmpty(vs);
        if (!empty && vs.size() > 1) {
            result.addError("查询结果存在多条数据");
            return result;
        }
        result.setResult(empty ? null : vs.get(0));
        return result;
    }

    @Override
    public ServiceResult queryList(V entity) {
        ServiceResult result = new ServiceResult();
        if (entity == null) {
            result.addError("入参不能为空");
            return result;
        }
        List<V> vs = targetDAO.selectListByCondition(entity);
        result.setResult(vs);
        return result;
    }

    @Override
    public PageInfo<V> findPage(V entity, Pager page) throws IllegalArgumentException {
        // 设置分页插件分页参数
        if( page == null) {
            throw new IllegalArgumentException("分页参数不能为空");
        }
        if (StringUtils.isBlank(page.getOrderBy())) {
            PageHelper.startPage(page.getPage(), page.getRows());
        } else {
            PageHelper.startPage(page.getPage(), page.getRows(),page.getOrderBy());
        }

        // 调用DAO层分页查询方法
        List<V> list = targetDAO.selectListWithPage(entity,page);
        return new PageInfo<V>(list);
    }


    @Override
    public Long queryCount(V entity) {
        return targetDAO.count(entity);
    }

    @Override
    public void updateMap(Map<Object, Object> entityMap) {
        targetDAO.updateBatchViaMap(entityMap);
    }


    @Override
    public Object selectMaxId() {
        return targetDAO.selectMaxId();
    }
}
