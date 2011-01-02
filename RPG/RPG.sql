--
-- RPG Data Structures
--
-- This script is always re-runnable.
--
-- Simply run again to upgrade.
--

--
-- Table structure for table `rpg_players`
--

CREATE TABLE IF NOT EXISTS `rpg_players` 
(
  `name` varchar(32) NOT NULL,
  `firstlogin` datetime NOT NULL,
  `lastlogin` datetime NOT NULL,
  `lastdisconnect` datetime,
  `online` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB;

--
-- Table structure for table `rpg_text`
--

CREATE TABLE IF NOT EXISTS `rpg_text` 
(
  `id` int(10) unsigned NOT NULL,
  `text` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

--
-- Table structure for table `rpg_magic`
--

CREATE TABLE IF NOT EXISTS `rpg_magic ` 
(
  `name` varchar(32) NOT NULL,
  `command` varchar(255) NOT NULL
  PRIMARY KEY (`name`)
) ENGINE=InnoDB;