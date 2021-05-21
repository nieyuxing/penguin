DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
    `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` varchar(63) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
    `sex` int(1) DEFAULT '0' COMMENT '性别,0-男,1-女',
    `ismarry` int(1) DEFAULT '0' COMMENT '婚姻状态,0-未婚 1-已婚 2-离异',
    `nation` int(3) DEFAULT '0' COMMENT '国籍ID,0-中国',
    `degree` int(1) DEFAULT '0' COMMENT '最高学历',
    `college` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '毕业学校',
    `sex` int(2) DEFAULT '0' COMMENT '证件类型,0-身份证',
    `idno` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '证件号',
    `password` varchar(60) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
    `qq` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'QQ',
    `vchat` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信号',
    `phone` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
    `email` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱地址',
    `description` varchar(63) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '个人描述',
    `avatar_img_url` varchar(63) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
    `approvestatus` int(8) DEFAULT '0' COMMENT '审核状态,0-未审核,1-审核通过,2-不通过',
    `state` int(8) DEFAULT '0' COMMENT '当前账号状态,0-正常,1-禁用',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `addr` varchar(256) COLLATE utf8mb4_unicode_ci COMMENT '住址',
    `addr` varchar(256) COLLATE utf8mb4_unicode_ci COMMENT '简历',
    `attribute1` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预留字段1',
    `attribute2` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预留字段2',
    `attribute3` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预留字段3',
    `attribute4` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预留字段4',
    `attribute5` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预留字段5',
        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ----------------------------
-- Table structure for t_penguin_question
-- ----------------------------
DROP TABLE IF EXISTS `t_penguin_question`;
CREATE TABLE `t_penguin_question` (
                                      `id` int(8) NOT NULL AUTO_INCREMENT,
                                      `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '题目标题',
                                      `content` text COLLATE utf8mb4_unicode_ci COMMENT '题目内容',
                                      `question_type` int(8) DEFAULT NULL COMMENT '题目类型,0表示单项选择题,1表示多项选择题,2表示问答题,3表示编程题',
                                      `option_a` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '选项A',
                                      `option_b` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '选项B',
                                      `option_c` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '选项C',
                                      `option_d` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '选项D',
                                      `answer` text COLLATE utf8mb4_unicode_ci COMMENT '答案',
                                      `parse` text COLLATE utf8mb4_unicode_ci COMMENT '答案解析',
                                      `subject_id` int(8) DEFAULT NULL COMMENT '所属部门',
                                      `contest_id` int(8) DEFAULT NULL COMMENT '试卷ID',
                                      `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      `score` int(8) DEFAULT NULL COMMENT '题目分值',
                                      `difficulty` int(8) DEFAULT '1' COMMENT '题目难度',
                                      `state` int(8) DEFAULT '1' COMMENT '0表示未考试题目,1表示已考试题目',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;




