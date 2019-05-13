DROP SCHEMA IF EXISTS pokedb;

-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema pokedb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema pokedb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pokedb` DEFAULT CHARACTER SET utf8 ;
USE `pokedb` ;

-- -----------------------------------------------------
-- Table `pokedb`.`pokemon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokedb`.`pokemon` (
  `pokemon_id` INT(3) NOT NULL,
  `name` VARCHAR(15) NOT NULL,
  `health` INT(4) NOT NULL,
  `attack` INT(4) NOT NULL,
  `defense` INT(4) NOT NULL,
  `speed` INT(4) NOT NULL,
  `first_type` ENUM('', 'Bug', 'Dragon', 'Electric', 'Fighting', 'Fire', 'Flying', 'Ghost', 'Grass', 'Ground', 'Ice', 'Normal', 'Poison', 'Psychic', 'Rock', 'Water') NOT NULL DEFAULT '',
  `second_type` ENUM('', 'Bug', 'Dragon', 'Electric', 'Fighting', 'Fire', 'Flying', 'Ghost', 'Grass', 'Ground', 'Ice', 'Normal', 'Poison', 'Psychic', 'Rock', 'Water') NOT NULL DEFAULT '',
  PRIMARY KEY (`pokemon_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pokedb`.`user_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokedb`.`user_info` (
  `email` VARCHAR(30) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `username` VARCHAR(16) NULL DEFAULT NULL,
  `login_bonus` DATE NULL DEFAULT NULL,
  `win_count` INT(6) NULL DEFAULT NULL,
  `loss_count` INT(6) NULL DEFAULT NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pokedb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokedb`.`user` (
  `user_id` INT(5) NOT NULL AUTO_INCREMENT,
  `is_professor` TINYINT(4) NOT NULL DEFAULT '0',
  `user_info_email` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  INDEX `fk_user_user_info1_idx` (`user_info_email` ASC) VISIBLE,
  CONSTRAINT `fk_user_user_info1`
    FOREIGN KEY (`user_info_email`)
    REFERENCES `pokedb`.`user_info` (`email`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pokedb`.`user_collection`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokedb`.`user_collection` (
  `user_id` INT(5) NOT NULL,
  `pokemon_id` INT(3) NOT NULL,
  `nickname` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`, `pokemon_id`),
  INDEX `fk_user_has_pokemon_pokemon1_idx` (`pokemon_id` ASC) VISIBLE,
  INDEX `fk_user_has_pokemon_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_pokemon_pokemon1`
    FOREIGN KEY (`pokemon_id`)
    REFERENCES `pokedb`.`pokemon` (`pokemon_id`),
  CONSTRAINT `fk_user_has_pokemon_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `pokedb`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pokedb`.`user_has_team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokedb`.`user_has_team` (
  `user_id` INT(5) NOT NULL,
  `user_collection_id` INT(5) NOT NULL,
  `pokemon_id` INT(3) NOT NULL,
  PRIMARY KEY (`user_id`, `user_collection_id`, `pokemon_id`),
  INDEX `fk_user_has_user_collection_user_collection1_idx` (`user_collection_id` ASC, `pokemon_id` ASC) VISIBLE,
  INDEX `fk_user_has_user_collection_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_user_collection_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `pokedb`.`user` (`user_id`),
  CONSTRAINT `fk_user_has_user_collection_user_collection1`
    FOREIGN KEY (`user_collection_id` , `pokemon_id`)
    REFERENCES `pokedb`.`user_collection` (`user_id` , `pokemon_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- Insert users into the database
insert into user_info (email, password) values 
('oak@professor', '12345');

insert into user_info (email, password, username, login_bonus, win_count, loss_count) values
('ash@trainer', '12345', 'Ash', curdate(), 0, 0);

-- Create stats for the test users to use
insert into user (is_professor, user_info_email) values
(true, 'oak@professor'),
(false, 'ash@trainer');

-- Insert pokemon to use
insert into pokemon (pokemon_id, name, health, attack, defense, speed, first_type, second_type) values
(1, 'Bulbasaur', 100, 10, 10, 10, 'Grass', 'Poison'),
(2, 'Ivysaur', 100, 10, 10, 10, 'Grass', 'Poison'),
(3, 'Venusaur', 100, 10, 10, 10, 'Grass', 'Poison'),
(6, 'Charizard', 100, 10, 10, 10, 'Fire', 'Flying');

insert into pokemon (pokemon_id, name, health, attack, defense, speed, first_type) values
(4, 'Charmander', 100, 10, 10, 10, 'Fire'),
(5, 'Charmeleon', 100, 10, 10, 10, 'Fire'),
(7, 'Squirtle', 100, 10, 10, 10, 'Water'),
(8, 'Wartortle', 100, 10, 10, 10, 'Water'),
(9, 'Blastoise', 100, 10, 10, 10, 'Water'),
(25, 'Pikachu', 100, 10, 10, 10, 'Electric'),
(26, 'Raichu', 100, 10, 10, 10, 'Electric');


-- Insert some pokemon for the Trainer Ash to own
INSERT INTO user_collection (user_id, pokemon_id, nickname) VALUES
((SELECT user_id FROM user WHERE user_info_email LIKE 'ash@trainer'), 25, 'ElectroRat');

-- Insert a team for Ash to use
INSERT INTO user_has_team (user_id, user_collection_id, pokemon_id) VALUES
((SELECT user_id FROM user WHERE user_info_email LIKE 'ash@trainer'), (SELECT user_id FROM user_collection WHERE user_id = (SELECT user_id FROM user WHERE user_info_email LIKE 'ash@trainer')), (SELECT pokemon_id FROM user_collection WHERE user_id = (SELECT user_id FROM user WHERE user_info_email LIKE 'ash@trainer')));


-- some select commands to test and use in the database loader

-- select * from user;
-- select * from user_info;
-- select * from pokemon;
-- select * from user_has_team;

-- select is_professor, email, password, user_id from user, user_info where user.user_info_email like user_info.email;
-- select email, username, login_bonus, win_count, loss_count from user_info where email like 'ash@trainer';
-- select pokemon_id, nickname from user_collection where user_id = (select user.user_id from user where user_info_email like 'ash@trainer');

-- select * from user_collection;
-- select user_collection.pokemon_id, nickname from user_has_team, user_collection where user_has_team.user_id = user_collection.user_id and user_collection.user_id = 2;

-- SELECT user_id FROM user WHERE user_info_email LIKE 'ash@trainer';
-- SELECT win_count, loss_count FROM user_info WHERE email LIKE 'ash@trainer';

-- INSERT INTO user_collection (user_id, pokemon_id, nickname) VALUES
-- ((SELECT user_id FROM user WHERE user_info_email LIKE 'peter@hkr'), 4, 'Charmander')