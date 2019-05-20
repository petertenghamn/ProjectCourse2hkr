DROP SCHEMA IF EXISTS pokedb;

-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema pokeDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema pokeDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pokeDB`;
USE `pokeDB`;

-- -----------------------------------------------------
-- Table `pokeDB`.`pokemon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokeDB`.`pokemon` (
  `pokemon_id` INT(4) NOT NULL,
  `name` VARCHAR(15) NOT NULL,
  `health` INT(4) NOT NULL,
  `attack` INT(4) NOT NULL,
  `defense` INT(4) NOT NULL,
  `speed` INT(4) NOT NULL,
  `first_type` ENUM('', 'Bug', 'Dragon', 'Electric', 'Fighting', 'Fire', 'Flying', 'Ghost', 'Grass', 'Ground', 'Ice', 'Normal', 'Poison', 'Psychic', 'Rock', 'Water') NOT NULL DEFAULT '',
  `second_type` ENUM('', 'Bug', 'Dragon', 'Electric', 'Fighting', 'Fire', 'Flying', 'Ghost', 'Grass', 'Ground', 'Ice', 'Normal', 'Poison', 'Psychic', 'Rock', 'Water') NOT NULL DEFAULT '',
  `cost` INT(3) NOT NULL DEFAULT 50,
  PRIMARY KEY (`pokemon_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pokeDB`.`user_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokeDB`.`user_info` (
  `email` VARCHAR(30) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `username` VARCHAR(16) NULL DEFAULT NULL,
  `currency` INT(7) NULL DEFAULT NULL,
  `login_bonus` DATE NULL DEFAULT NULL,
  `win_count` INT(6) NULL DEFAULT NULL,
  `loss_count` INT(6) NULL DEFAULT NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pokeDB`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokeDB`.`user` (
  `user_id` INT(5) NOT NULL AUTO_INCREMENT,
  `is_professor` TINYINT(4) NOT NULL DEFAULT '0',
  `user_info_email` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  INDEX `fk_user_user_info_idx` (`user_info_email` ASC) VISIBLE,
  CONSTRAINT `fk_user_user_info`
    FOREIGN KEY (`user_info_email`)
    REFERENCES `pokeDB`.`user_info` (`email`)
    ON DELETE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pokeDB`.`collection`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokeDB`.`collection` (
  `nickname` VARCHAR(16) NOT NULL,
  `user_id` INT(5) NOT NULL,
  `pokemon_id` INT(4) NOT NULL,
  PRIMARY KEY (`nickname`, `user_id`, `pokemon_id`),
  INDEX `fk_collection_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_collection_pokemon1_idx` (`pokemon_id` ASC) VISIBLE,
  CONSTRAINT `fk_collection_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `pokeDB`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_collection_pokemon1`
    FOREIGN KEY (`pokemon_id`)
    REFERENCES `pokeDB`.`pokemon` (`pokemon_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pokeDB`.`user_has_team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokeDB`.`user_has_team` (
  `user_id` INT(5) NOT NULL,
  `collection_nickname` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`user_id`, `collection_nickname`),
  INDEX `fk_user_has_collection_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_user_has_team_collection1_idx` (`collection_nickname` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_collection_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `pokeDB`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_team_collection1`
    FOREIGN KEY (`collection_nickname`)
    REFERENCES `pokeDB`.`collection` (`nickname`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- Insert users into the database
insert into user_info (email, password) values 
('oak@professor', '12345');

insert into user_info (email, password, username, currency, login_bonus, win_count, loss_count) values
('ash@trainer', '12345', 'Ash', 300, curdate(), 0, 0);

-- Create stats for the test users to use
insert into user (is_professor, user_info_email) values
(true, 'oak@professor'),
(false, 'ash@trainer');

-- Insert pokemon to use
insert into pokemon (pokemon_id, name, health, attack, defense, speed, first_type, second_type, cost) values
(1, 'Bulbasaur', 45, 4, 4, 45, 'Grass', 'Poison', 50),
(2, 'Ivysaur', 60, 6, 6, 60, 'Grass', 'Poison', 75),
(3, 'Venusaur', 80, 8, 8, 80, 'Grass', 'Poison', 100),
(6, 'Charizard', 78, 8, 7, 100, 'Fire', 'Flying', 100);

insert into pokemon (pokemon_id, name, health, attack, defense, speed, first_type, cost) values
(4, 'Charmander', 39, 5, 4, 65, 'Fire', 50),
(5, 'Charmeleon', 58, 6, 5, 80, 'Fire', 75),
(7, 'Squirtle', 44, 4, 6, 43, 'Water', 50),
(8, 'Wartortle', 59, 6, 8, 58, 'Water', 75),
(9, 'Blastoise', 79, 8, 10, 78, 'Water', 100),
(25, 'Pikachu', 100, 5, 4, 90, 'Electric', 100),
(26, 'Raichu', 60, 9, 5, 110, 'Electric', 100);


-- Insert some pokemon for the Trainer Ash to own
INSERT INTO collection (nickname, user_id, pokemon_id) VALUES
('ElectroRat', (SELECT user_id FROM user WHERE user_info_email LIKE 'ash@trainer'), 0025);

-- Insert a team for Ash to use
INSERT INTO user_has_team (collection_nickname, user_id) VALUES
 ((SELECT nickname FROM collection WHERE nickname LIKE 'ElectroRat'),
 (SELECT user_id FROM user WHERE user_info_email LIKE 'ash@trainer'));
 
 
 
-- some select commands to test and use in the database loader

-- SELECT user_has_team.pokemon_id, collection.nickname FROM user_has_team, collection where user_has_team.user_id = 
-- (SELECT user_has_team.user_id FROM user WHERE user_info_email LIKE 'ash@trainer');

 -- UPDATE user_info SET currency = 500 WHERE email LIKE 'peter@hkr';

-- select * from user;
-- select * from user_info;
 select * from pokemon;
-- SELECT cost FROM pokemon WHERE pokemon_id = 1;
-- select * from collection;
-- select * from user_has_team;

-- SELECT pokemon_id, nickname FROM collection INNER JOIN user_has_team ON collection.user_id LIKE user_has_team.user_id AND collection.nickname LIKE user_has_team.collection_nickname;
-- SELECT pokemon_id, nickname FROM collection INNER JOIN user_has_team ON collection.user_id LIKE user_has_team.user_id AND collection.user_id LIKE '2';
-- SELECT email, username, currency, win_count, loss_count FROM user_info INNER JOIN user ON email LIKE user_info_email AND is_professor = '0';

-- select is_professor, email, password, user_id from user, user_info where user.user_info_email like user_info.email;
-- select email, username, login_bonus, win_count, loss_count from user_info where email like 'ash@trainer';
-- SELECT login_bonus FROM user_info WHERE email LIKE 'ash@trainer';
-- select pokemon_id, nickname from collection where user_id = (select user.user_id from user where user_info_email like 'ash@trainer');

-- select * from collection;
-- SELECT * FROM collection where user_id = (SELECT user_id FROM user WHERE user_info_email LIKE 'ash@trainer');
-- select collection.pokemon_id, nickname from user_has_team, collection where user_has_team.user_id = collection.user_id and collection.user_id = 2;

-- SELECT user_id FROM user WHERE user_info_email LIKE 'ash@trainer';
-- SELECT win_count, loss_count FROM user_info WHERE email LIKE 'ash@trainer';

-- DELETE FROM user_has_team WHERE user_id LIKE (SELECT user_id FROM user WHERE user_info_email LIKE 'ash@trainer') AND pokemon_id = 25;
-- SELECT * FROM user_has_team;

-- DELETE FROM collection WHERE user_id LIKE (SELECT user_id FROM user WHERE user_info_email LIKE 'ash@trainer') AND pokemon_id = 25;
-- select * from collection;

-- UPDATE user_info SET currency = 250, login_bonus = curdate(), win_count = 1, loss_count = 2 WHERE email LIKE 'ash@trainer';
-- UPDATE user_info SET login_bonus = '2019-05-09' WHERE email LIKE 'ash@trainer';
-- UPDATE user_info SET login_bonus = curdate() WHERE email LIKE 'ash@trainer';
-- select * from user_info;