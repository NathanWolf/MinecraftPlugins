--
-- RPGMagic Default setup
--
-- This script is an example of how RPGMagic can be
-- used in combination with RPGSpells and some of the built-in commands.
--
-- Running this script is optional. It can be re-run, but it will overwrite any changes you've
-- made to any of the default commands.
--

--
-- Default data for rpg_magic
--

SET @command = '/spell blink';
SET @name = 'blink';
INSERT INTO rpg_magic (name, command) VALUES (@name, @command)
	ON DUPLICATE KEY UPDATE name = @name, command = @command;

SET @command = '/spell heal';
SET @name = 'heal';
INSERT INTO rpg_magic (name, command) VALUES (@name, @command)
	ON DUPLICATE KEY UPDATE name = @name, command = @command;
	
SET @command = '/time day';
SET @name = 'day';
INSERT INTO rpg_magic (name, command) VALUES (@name, @command)
	ON DUPLICATE KEY UPDATE name = @name, command = @command;
	