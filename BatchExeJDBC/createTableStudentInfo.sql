-- Table "studentinfo" DDL

CREATE TABLE `studentinfo` (
  `id` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(10) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `grade` decimal(6,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
