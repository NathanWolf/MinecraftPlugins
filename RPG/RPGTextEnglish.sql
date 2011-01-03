--
-- RPG Data - English Language
--

-- This script is always re-runnable, but
-- re-running will overwrite any customizations
-- made to your database.

--
-- Run the schema script (RPG.sql) first!
-- 

--
-- TEXT 
--

-- RPG prefix, used when issues responses from the RPG plugin.
SET @text = '';
SET @index = 0;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;

-- Usage information
SET @text = 'Use:@/rpg players : List all of the players.';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;

-- Help text
SET @text = ' - Access RPG commands.';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;

-- Prefix for player online time
SET @text = 'online since ';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;

-- Prefix for player offline time
SET @text = 'offline since ';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;
	
-- Cast usage information
SET @text = 'Use:@/cast <spell>:@blink : teleport to your target@heal : heal yourself';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;

-- Cast help
SET @text = ' - View and cast learned spells';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;		

-- Tried to blink without a target
SET @text = 'Nowhere to blink to';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;
	
-- Tried to blink too far
SET @text = 'You can''t blink that far';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;

-- Nowhere to blink to
SET @text = 'You can''t blink there';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;
	
-- Casted blink
SET @text = 'Blink!';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;
	
-- Viewed someone casting blink
SET @text = '[caster] blinks!';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;
		
-- Casted heal
SET @text = 'You heal yourself';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;
	
-- Viewed someone casting heal
SET @text = '[caster] heals himself';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;
	
-- Health already full
SET @text = 'Your health is already full';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;
	
-- Help text for "magic" entry
SET @text = 'Cast magic spells';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;
	
-- Magic spell list
SET @text = 'Available spells:';
SET @index := @index + 1;
INSERT INTO rpg_text (id, text) VALUES (@index, @text)
	ON DUPLICATE KEY UPDATE id = @index, text = @text;