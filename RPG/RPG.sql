--
-- RPG Data Structures
--
-- This script is always re-runnable.
--
-- Simply run again to upgrade.
--

--
-- Table structure for table rpg_players
--

CREATE TABLE IF NOT EXISTS rpg_players 
(
  name varchar(32) NOT NULL,
  firstlogin datetime NOT NULL,
  lastlogin datetime NOT NULL,
  lastdisconnect datetime,
  online tinyint(1) DEFAULT '0',
  PRIMARY KEY (name)
) ENGINE=InnoDB;

--
-- Table structure for table rpg_text
--

CREATE TABLE IF NOT EXISTS rpg_text 
(
  id int(10) unsigned NOT NULL,
  text varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

--
-- Table structure for table rpg_magic
--

CREATE TABLE IF NOT EXISTS rpg_magic 
(
  name varchar(32) NOT NULL,
  command varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  initialcost double DEFAULT '0',
  costpersecond double DEFAULT '0',
  PRIMARY KEY (name)
) ENGINE=InnoDB;

--
-- Table structure for table rpg_wands
--

CREATE TABLE IF NOT EXISTS rpg_wands 
(
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  playerName varchar(32) NOT NULL,
  name varchar(255) NOT NULL,
  currentCommandId int(10),
  order int(10) DEFAULT '0', 
  PRIMARY KEY (id),
  UNIQUE(playerName, name),
  INDEX (playerName),
  FOREIGN KEY (playerName) REFERENCES rpg_players(name)
) ENGINE=InnoDB;


--
-- Table structure for table rpg_wand_commands
--

CREATE TABLE IF NOT EXISTS rpg_wand_commands 
(
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  wandId int(10) NOT NULL,
  command varchar(255) NOT NULL,
  order int(10) DEFAULT '0',
  PRIMARY KEY (id),
  INDEX (wandId),
  FOREIGN KEY (wandId) REFERENCES rpg_wands(id)
) ENGINE=InnoDB;


--
-- Table structure for table rpg_player_wands
--

CREATE TABLE IF NOT EXISTS rpg_player_wands 
(
  playerName varchar(32) NOT NULL,
  currentWandId int(10),
  PRIMARY KEY (playerName),
  FOREIGN KEY (playerName) REFERENCES rpg_players(name),
  INDEX(currentWandId),
  FOREIGN KEY (currentWandId) REFERENCES rpg_wands(id)
) ENGINE=InnoDB;
