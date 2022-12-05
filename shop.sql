create database `geth`;
use `geth`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(18),
  `email` varchar(64),
  `password` varchar(10),
  `address` varchar(64),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32),
  `price` float,
  `img_url` varchar(32),
  `seller_address` varchar(64),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `goods` VALUES (1, '时尚男装A', 0.3, 'img/img-4.jpg', '在geth端选择一个账户地址放进去');
INSERT INTO `goods` VALUES (2, '复古男装B', 0.1, 'img/img-2.jpg', '在geth端选择一个账户地址放进去');
INSERT INTO `goods` VALUES (3, '古典女装A', 2.1, 'img/img-3.jpg', '在geth端选择一个账户地址放进去');
INSERT INTO `goods` VALUES (4, '典雅女装B', 1.1, 'img/img-1.jpg', '在geth端选择一个账户地址放进去');


CREATE TABLE `order_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img_url` varchar(32),
  `goods_name` varchar(32),
  `order_price` float,
  `buy_time` datetime,
  `pay_time` datetime,
  `status` int(11),
  `transaction_hash` varchar(128),
  `order_address` varchar(64),
  `user_email` varchar(64),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;