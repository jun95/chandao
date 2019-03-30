CREATE TABLE `cd_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` char(30) NOT NULL DEFAULT '' COMMENT '账号',
  `password` char(32) NOT NULL DEFAULT '' COMMENT '密码',
  `type` int(1) NOT NULL DEFAULT '2' COMMENT '1:管理员；2：普通用户',
  `realname` varchar(100) DEFAULT '' COMMENT '真实姓名',
  `nickname` char(60) DEFAULT '' COMMENT '昵称',
  `mobile` char(11) DEFAULT '' COMMENT '手机号',
  `gender` int(1) DEFAULT '1' COMMENT '性别,1:男；2：女',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `deleted` int(1) NOT NULL DEFAULT '1' COMMENT '删除标记，1：不删除；0：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `cd_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `rid` bigint(20) DEFAULT NULL COMMENT '角色ID',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

CREATE TABLE `cd_u_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

CREATE TABLE `cd_u_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父权限',
  `url` varchar(255) DEFAULT NULL COMMENT '权限url',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `type` int(1) DEFAULT NULL COMMENT '权限类型,1:菜单;2：按钮',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限表';

CREATE TABLE `cd_u_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rid` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '权限ID',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色权限关联表';

CREATE TABLE `cd_group` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(30) NOT NULL COMMENT '组名称',
  `description` varchar(255) NOT NULL COMMENT '组描述',
  `create_by` bigint(20) NOT NULL COMMENT '创建人id',
  `create_name` varchar(30) NOT NULL COMMENT '创建人账号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目组表';

CREATE TABLE `cd_usergroup` (
  `id` bigint(20) NOT NULL auto_increment,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `account` varchar(30) NOT NULL COMMENT '用户账号',
  `group_id` bigint(20) NOT NULL COMMENT '项目组ID',
  `create_by` bigint(20) NOT NULL '创建人id',
  `create_name` varchar(30) NOT NULL COMMENT '创建人账号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目组成员关系表';

CREATE TABLE IF NOT EXISTS `cd_project` (
  `id` bigint(20) NOT NULL auto_increment,
  `group_id` bigint(20) NOT NULL COMMENT '项目组ID',
  `group_name` varchar(30) NOT NULL COMMENT '项目组名称',
  `name` varchar(90) NOT NULL COMMENT '项目名称',
  `begin` date NOT NULL COMMENT '项目开始时间',
  `end` date NOT NULL COMMENT '项目结束时间',
  `days` smallint(5) NOT NULL COMMENT '项目耗时，以天为单位',
  `status` varchar(10) NOT NULL COMMENT '项目状态；有done:已关闭、doing:进行中、wait：待开始',
  `description` text NOT NULL COMMENT '项目描述',
  `create_by` bigint(20) NOT NULL '创建人id',
  `create_name` varchar(30) NOT NULL COMMENT '创建人账号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `closed_by` bigint(20) NULL COMMENT '关闭人id',
  `closed_name` varchar(30) NULL COMMENT '关闭人账号',
  `closed_date` datetime NULL COMMENT '关闭时间',
  `canceled_by` bigint(20) NULL COMMENT '取消人ID',
  `canceled_name` varchar(30) NULL COMMENT '取消人账号',
  `canceled_date` datetime NULL COMMENT '取消时间',
  `deleted` int(1) NOT NULL default 1 COMMENT '删除标记，1：不删除；0：已删除',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目表';

CREATE TABLE IF NOT EXISTS `cd_requirement` (
  `id` bigint(20) NOT NULL auto_increment,
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `project_name` varchar(90) NOT NULL COMMENT '项目标题',
  `title` varchar(90) NOT NULL COMMENT '需求标题',
  `begin` date NOT NULL COMMENT '需求开始时间',
  `end` date NOT NULL COMMENT '需求结束时间',
  `status` varchar(10) NOT NULL COMMENT '状态；有done:已关闭、doing:进行中、wait：待开始',
  `description` text NOT NULL COMMENT '需求描述',
  `create_by` bigint(20) NOT NULL '创建人id',
  `create_name` varchar(30) NOT NULL COMMENT '创建人账号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `closed_by` bigint(20) NULL COMMENT '关闭人id',
  `closed_name` varchar(30) NULL COMMENT '关闭人账号',
  `closed_date` datetime NULL COMMENT '关闭时间',
  `canceled_by` bigint(20) NULL COMMENT '取消人ID',
  `canceled_name` varchar(30) NULL COMMENT '取消人账号',
  `canceled_date` datetime NULL COMMENT '取消时间',
  `deleted` int(1) NOT NULL default 1 COMMENT '删除标记，1：不删除；0：已删除',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求表';

CREATE TABLE IF NOT EXISTS `cd_task` (
  `id` bigint(20) NOT NULL auto_increment,
  `parent` bigint(20) NULL DEFAULT '0' COMMENT '父任务id',
  `requirement_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '需求id',
  `requirement_name` varchar(90) NOT NULL COMMENT '需求名称',
  `project_id` bigint(20) NOT NULL default '0' COMMENT '项目id',
  `project_name` varchar(90) NOT NULL COMMENT '项目名称',
  `from_bug` bigint(20) NULL COMMENT '来自哪个bug',
  `name` varchar(255) NOT NULL COMMENT '任务名称',
  `type` int(2) NOT NULL COMMENT '任务类型；1：开发',
  `pri` tinyint(3) NOT NULL default '0' COMMENT '优先级',
  `estimate` float NOT NULL COMMENT '预计耗时',
  `consumed` float NULL COMMENT '耗时',
  `status` enum('wait','doing','done','pause','cancel','closed') NOT NULL default 'wait' COMMENT '任务状态，done：已完成；doing：进行中；wait：待开始;pause:已暂停；closed：已关闭；cancel：已取消',
  `description` text NOT NULL COMMENT '任务描述',
  `opened_by` bigint(20) NULL COMMENT '激活人id',
  `opened_name` varchar(30) NULL COMMENT '激活人账号',
  `opened_date` datetime NULL COMMENT '激活时间',
  `assigned_by` bigint(20) NULL COMMENT '指派人id',
  `assigned_by_name` varchar(30) NULL COMMENT '指派人账号',
  `assigned_date` datetime NULL COMMENT '指派时间',
  `finished_by` bigint(20) NULL COMMENT '完成人id',
  `finished_name` varchar(30) NULL COMMENT '完成人账号',
  `finished_date` datetime NULL COMMENT '完成时间',
  `closed_by` bigint(20) NULL COMMENT '关闭人id',
  `closed_name` varchar(30) NULL COMMENT '关闭人账号',
  `closed_date` datetime NULL COMMENT '关闭时间',
  `canceled_by` bigint(20) NULL COMMENT '取消人ID',
  `canceled_name` varchar(30) NULL COMMENT '取消人账号',
  `canceled_date` datetime NULL COMMENT '取消时间',
  `closed_reason` varchar(30) NULL COMMENT '关闭原因',
  `last_edited_by` bigint(20) NULL COMMENT '最后编辑人id',
  `last_edited_name` varchar(30) NULL COMMENT '最后编辑人账号',
  `last_edited_date` datetime NULL COMMENT '最后编辑时间',
  `deleted` int(1) NOT NULL default 1,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务表';

CREATE TABLE IF NOT EXISTS `cd_testtask` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` char(90) NOT NULL,
  `project_id` bigint(20) NOT NULL default '0',
  `project_name` varchar(90) NOT NULL COMMENT '项目标题',
  `requirement_id` bigint(20) NOT NULL default '0',
  `requirement_name` varchar(90) NOT NULL COMMENT '需求名称',
  `owner` varchar(30) NOT NULL COMMENT '测试任务创建人',
  `begin` date NOT NULL COMMENT '测试任务开始时间',
  `end` date NOT NULL COMMENT '测试任务结束时间',
  `description` text NOT NULL COMMENT '测试任务描述',
  `status` enum('doing','wait','done','finished') NOT NULL DEFAULT 'wait' COMMENT '任务状态，finished:已完成；done：已关闭；doing：进行中；wait：待开始',
  `opened_by` bigint(20) NULL COMMENT '激活人id',
  `opened_name` varchar(30) NULL COMMENT '激活人账号',
  `opened_date` datetime NULL COMMENT '激活时间',
  `finished_by` bigint(20) NULL COMMENT '完成人id',
  `finished_name` varchar(30) NULL COMMENT '完成人账号',
  `finished_date` datetime NULL COMMENT '完成时间',
  `closed_by` bigint(20) NULL COMMENT '关闭人id',
  `closed_name` varchar(30) NULL COMMENT '关闭人账号',
  `closed_date` datetime NULL COMMENT '关闭时间',
  `closed_reason` varchar(30) NULL COMMENT '测试报告',
  `assigned_by` bigint(20) NULL COMMENT '指派人id',
  `assigned_by_name` varchar(30) NULL COMMENT '指派人账号',
  `assigned_date` datetime NULL COMMENT '指派时间',
  `deleted` int(1) NOT NULL default 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试任务表';

CREATE TABLE IF NOT EXISTS `cd_bug` (
  `id` bigint(20) NOT NULL auto_increment,
  `project_id` bigint(20) NOT NULL default '0' COMMENT '项目id',
  `project_name` varchar(90) NOT NULL COMMENT '项目标题',
  `task_id` bigint(20) NULL default '0' COMMENT '任务id',
  `title` varchar(255) NULL COMMENT 'bug标题',
  `keywords` varchar(255) NOT NULL COMMENT '关键词',
  `severity` tinyint(4) NULL default '0' COMMENT '严重性',
  `pri` tinyint(3) NOT NULL COMMENT '优先级',
  `steps` text NOT NULL COMMENT '重现步骤',
  `status` enum('active','resolved','closed') NOT NULL default 'active',
  `activated_count` smallint(6) NULL default 0 COMMENT '激活次数',
  `activated_date` datetime NULL COMMENT '激活日期',
  `opened_by` bigint(20) NULL COMMENT '激活人id',
  `opened_name` varchar(30) NULL COMMENT '激活人账号',
  `opened_date` datetime NULL COMMENT '激活时间',
  `assigned_by` bigint(20) NULL COMMENT '指派人id',
  `assigned_by_name` varchar(30) NULL COMMENT '指派人账号',
  `assigned_date` datetime NULL COMMENT '指派时间',
  `resolved_by` bigint(20) NULL COMMENT '解决人id',
  `resolved_name` varchar(30) NULL COMMENT '解决人账号',
  `resolution` varchar(30) NULL COMMENT '解决方案',
  `resolved_date` datetime NULL COMMENT '解决日期',
  `closed_by` bigint(20) NULL COMMENT '关闭人id',
  `closed_name` varchar(30) NULL COMMENT '关闭人账号',
  `closed_date` datetime NULL COMMENT '关闭时间',
  `duplicate_bug` mediumint(8) NULL COMMENT '重复bug',
  `link_bug` varchar(255) NULL COMMENT '关联bug',
  `testtask_id` bigint(20) NULL COMMENT '测试任务id',
  `last_edited_by` bigint(20) NULL COMMENT '最后编辑人id',
  `last_edited_name` varchar(30) NULL COMMENT '最后编辑人账号',
  `last_edited_date` datetime NULL COMMENT '最后编辑时间',
  `deleted` int(1) NOT NULL default 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='bug表';

CREATE TABLE IF NOT EXISTS `cd_action_log` (
  `id` bigint(20) NOT NULL auto_increment,
  `object_type` varchar(30) NOT NULL default '' COMMENT '日志类型；task,testtask,bug',
  `object_id` bigint(20) NOT NULL default '0' COMMENT '对应类型的id',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `action` varchar(30) NOT NULL COMMENT '动作',
  `status` varchar(30) NOT NULL DEFAULT 'wait' COMMENT '对应类型的状态',
  `previous_owner` bigint(20) NULL COMMENT '曾经拥有者',
  `assigned_by` bigint(20) NULL COMMENT '当前拥有者',
  `account` char(30) NULL default '' COMMENT '当前拥有者账号',
  `date` date NOT NULL COMMENT '创建日期',
  `comment` text NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务、测试任务，bug信息日志表';
