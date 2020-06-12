CREATE TABLE `accounts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `dob` date NOT NULL,
  `phone_num` varchar(15) NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  `created_by` varchar(25) NOT NULL,
  `created_on` timestamp NOT NULL,
  `modified_by` varchar(25) DEFAULT NULL,
  `modified_on` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `accounts_UN` (`phone_num`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
