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
SET @description = 'Teleport to the target location';
INSERT INTO rpg_magic (name, command, description) VALUES (@name, @command, @description)
	ON DUPLICATE KEY UPDATE name = @name, command = @command, description = @description;

SET @command = '/spell heal';
SET @name = 'heal';
SET @description = 'Heal yourself';
INSERT INTO rpg_magic (name, command, description) VALUES (@name, @command, @description)
	ON DUPLICATE KEY UPDATE name = @name, command = @command, description = @description;
	
SET @command = '/time day';
SET @name = 'day';
SET @description = 'Change the time to mid day';
INSERT INTO rpg_magic (name, command, description) VALUES (@name, @command, @description)
	ON DUPLICATE KEY UPDATE name = @name, command = @command, description = @description;
	