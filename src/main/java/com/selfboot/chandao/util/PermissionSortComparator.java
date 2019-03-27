package com.selfboot.chandao.util;

import com.selfboot.chandao.domain.CdUPermission;

import java.util.Comparator;

/**
 * Created by 87570 on 2019/3/27.
 */
public class PermissionSortComparator implements Comparator<CdUPermission> {

    @Override
    public int compare(CdUPermission o1, CdUPermission o2) {
        return o1.getSort() - o2.getSort();
    }
}
