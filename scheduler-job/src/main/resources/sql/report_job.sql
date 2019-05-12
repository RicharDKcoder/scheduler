CREATE TABLE `t_scheduler_report_trigger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `introduction` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `receive_list` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creator` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cron` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `strategy` int(2) DEFAULT NULL,
  `attachment` int(2) DEFAULT NULL,
  `str_sql` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `heads` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `next_point` timestamp NULL DEFAULT NULL,
  `cdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `class_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='report job触发器列表';