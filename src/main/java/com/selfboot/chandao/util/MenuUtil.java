package com.selfboot.chandao.util;

import com.selfboot.chandao.domain.CdUPermission;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 87570 on 2019/3/27.
 */
public class MenuUtil {

    private MenuUtil() {}

    public static List<CdUPermission> getMenu(List<CdUPermission> allMenu) {
        List<CdUPermission> result = new ArrayList<>();
        CdUPermission resultMenuDomain = null;
        //目录无限制
        for (CdUPermission menuItem : allMenu) {
            //第一层
            if (menuItem.getParentId() == 0) {
                resultMenuDomain = menuItem;
                resultMenuDomain.setChildren(getSonMenuList(menuItem.getId(),allMenu));
                result.add(resultMenuDomain);

                resultMenuDomain = null;
            }
        }

        Collections.sort(result,new PermissionSortComparator());

        return result;
    }

    private static List<CdUPermission> getSonMenuList(Long id ,List<CdUPermission> allMenu) {
        if (CollectionUtils.isEmpty(allMenu)) {
            return null;
        }

        List<CdUPermission> listvo = new ArrayList<>();
        for (CdUPermission menuItem : allMenu) {
            if (menuItem.getParentId().longValue() == id) {//找到父级相同的下级
                listvo.add(menuItem);
            }
        }
        if(listvo.size() > 0){

            Collections.sort(listvo,new PermissionSortComparator());

            for(CdUPermission vo:listvo){
                vo.setChildren(getSonMenuList(vo.getId(),allMenu));
            }
        }
        return listvo;
    }
}
