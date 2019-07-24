CREATE DATABASE `dev` ;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(45) NOT NULL COMMENT 'user name',
  `password` varchar(45) NOT NULL COMMENT 'password',
  `salt` varchar(45) NOT NULL COMMENT 'salt',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user` (`user_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='user';
