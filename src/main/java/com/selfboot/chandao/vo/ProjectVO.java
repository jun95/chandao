package com.selfboot.chandao.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by hwj on 2019/3/22.
 */
public class ProjectVO implements Serializable {

    private Long id;

    @NotBlank(message = "状态不能为空")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
