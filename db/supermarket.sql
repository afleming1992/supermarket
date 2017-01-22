--
-- Database: `supermarket`
--

-- --------------------------------------------------------

--
-- Table structure for table `basket`
--

CREATE TABLE IF NOT EXISTS `basket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `completed` tinyint(1) NOT NULL DEFAULT '0',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `finalTotal` decimal(10,2) DEFAULT '0.00',
  `totalSavings` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

-- --------------------------------------------------------

--
-- Table structure for table `basketItem`
--

CREATE TABLE IF NOT EXISTS `basketItem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `basketId` int(11) NOT NULL,
  `itemId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order` (`basketId`,`itemId`),
  KEY `item` (`itemId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=53 ;

-- --------------------------------------------------------

--
-- Table structure for table `basketPromotion`
--

CREATE TABLE IF NOT EXISTS `basketPromotion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `basketId` int(11) NOT NULL,
  `promotionId` int(11) NOT NULL,
  `price` decimal(10,2) DEFAULT '0.00',
  `totalSavings` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id`),
  KEY `order` (`basketId`),
  KEY `promotion` (`promotionId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=22 ;

-- --------------------------------------------------------

--
-- Table structure for table `basketPromotionItem`
--

CREATE TABLE IF NOT EXISTS `basketPromotionItem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `basketPromotionId` int(11) NOT NULL,
  `itemId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `orderPromotion` (`basketPromotionId`,`itemId`),
  KEY `item` (`itemId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=49 ;

-- --------------------------------------------------------

--
-- Table structure for table `freeItemPromotion`
--

CREATE TABLE IF NOT EXISTS `freeItemPromotion` (
  `promotionId` int(11) NOT NULL,
  `noOfFreeItems` int(11) NOT NULL,
  PRIMARY KEY (`promotionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE IF NOT EXISTS `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `description` text NOT NULL,
  `barcode` varchar(13) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `barcode` (`barcode`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

-- --------------------------------------------------------

--
-- Table structure for table `moneyOffPromotion`
--

CREATE TABLE IF NOT EXISTS `moneyOffPromotion` (
  `promotionId` int(11) NOT NULL,
  `totalPrice` decimal(10,2) NOT NULL,
  PRIMARY KEY (`promotionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `promotion`
--

CREATE TABLE IF NOT EXISTS `promotion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `active` tinyint(1) NOT NULL,
  `noOfItemsRequired` int(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

-- --------------------------------------------------------

--
-- Table structure for table `promotionItem`
--

CREATE TABLE IF NOT EXISTS `promotionItem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promotionId` int(11) NOT NULL,
  `itemId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `promotion` (`promotionId`,`itemId`),
  KEY `item` (`itemId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

-- --------------------------------------------------------

CREATE VIEW `promotion_v` AS
  SELECT p.id as promo_id, p.name as name, p.active as active, p.noOfItemsRequired as noOfItemsRequired,


--
-- Stand-in structure for view `promotion_v`
--
CREATE TABLE IF NOT EXISTS `promotion_v` (
`promo_id` int(11)
,`name` text
,`active` tinyint(1)
,`noOfItemsRequired` int(2)
,`totalPrice` decimal(10,2)
,`noOfFreeItems` int(11)
);
-- --------------------------------------------------------

--
-- Structure for view `promotion_v`
--
DROP TABLE IF EXISTS `promotion_v`;

CREATE VIEW `promotion_v` AS select `p`.`id` AS `promo_id`,`p`.`name` AS `name`,`p`.`active` AS `active`,`p`.`noOfItemsRequired` AS `noOfItemsRequired`,`m`.`totalPrice` AS `totalPrice`,`f`.`noOfFreeItems` AS `noOfFreeItems` from ((`promotion` `p` left join `freeItemPromotion` `f` on((`p`.`id` = `f`.`promotionId`))) left join `moneyOffPromotion` `m` on((`p`.`id` = `m`.`promotionId`)));

--
-- Constraints for dumped tables
--

--
-- Constraints for table `basketItem`
--
ALTER TABLE `basketItem`
  ADD CONSTRAINT `basketItem_ibfk_2` FOREIGN KEY (`itemId`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `basketItem_ibfk_1` FOREIGN KEY (`basketId`) REFERENCES `basket` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `basketPromotion`
--
ALTER TABLE `basketPromotion`
  ADD CONSTRAINT `basketPromotion_ibfk_1` FOREIGN KEY (`basketId`) REFERENCES `basket` (`id`) ON UPDATE NO ACTION,
  ADD CONSTRAINT `basketPromotion_ibfk_2` FOREIGN KEY (`promotionId`) REFERENCES `promotion` (`id`) ON UPDATE NO ACTION;

--
-- Constraints for table `basketPromotionItem`
--
ALTER TABLE `basketPromotionItem`
  ADD CONSTRAINT `basketPromotionItem_ibfk_4` FOREIGN KEY (`itemId`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `basketPromotionItem_ibfk_3` FOREIGN KEY (`basketPromotionId`) REFERENCES `basketPromotion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `freeItemPromotion`
--
ALTER TABLE `freeItemPromotion`
  ADD CONSTRAINT `freeItemPromotion_ibfk_1` FOREIGN KEY (`promotionId`) REFERENCES `promotion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `moneyOffPromotion`
--
ALTER TABLE `moneyOffPromotion`
  ADD CONSTRAINT `moneyOffPromotion_ibfk_1` FOREIGN KEY (`promotionId`) REFERENCES `promotion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `promotionItem`
--
ALTER TABLE `promotionItem`
  ADD CONSTRAINT `promotionItem_ibfk_2` FOREIGN KEY (`itemId`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `promotionItem_ibfk_1` FOREIGN KEY (`promotionId`) REFERENCES `promotion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
