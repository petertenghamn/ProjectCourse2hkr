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
CREATE SCHEMA IF NOT EXISTS `pokeDB` DEFAULT CHARACTER SET utf8 ;
USE `pokeDB` ;

-- -----------------------------------------------------
-- Table `pokeDB`.`user_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokeDB`.`user_info` (
  `email` VARCHAR(30) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `username` VARCHAR(16) NULL,
  `login_bonus` DATE NULL,
  `win_count` INT NULL,
  `loss_count` INT NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pokeDB`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokeDB`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `is_professor` TINYINT NOT NULL DEFAULT 0,
  `user_info_email` VARCHAR(30) NOT NULL,
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  PRIMARY KEY (`user_id`),
  INDEX `fk_user_user_info1_idx` (`user_info_email` ASC) VISIBLE,
  CONSTRAINT `fk_user_user_info1`
    FOREIGN KEY (`user_info_email`)
    REFERENCES `pokeDB`.`user_info` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pokeDB`.`pokemon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokeDB`.`pokemon` (
  `pokemon_id` INT NOT NULL,
  `name` VARCHAR(15) NOT NULL,
  `health` INT NOT NULL,
  `attack` INT NOT NULL,
  `defense` INT NOT NULL,
  `speed` INT NOT NULL,
  PRIMARY KEY (`pokemon_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pokeDB`.`pokemon_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokeDB`.`pokemon_type` (
  `type` ENUM('bug', 'dragon', 'electric', 'fighting', 'fire', 'flying', 'ghost', 'grass', 'ground', 'ice', 'normal', 'poison', 'psychic', 'rock', 'water') NOT NULL,
  PRIMARY KEY (`type`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pokeDB`.`user_collection`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokeDB`.`user_collection` (
  `user_id` INT NOT NULL,
  `pokemon_id` INT NOT NULL,
  `nickname` VARCHAR(30) NULL,
  PRIMARY KEY (`user_id`, `pokemon_id`),
  INDEX `fk_user_has_pokemon_pokemon1_idx` (`pokemon_id` ASC) VISIBLE,
  INDEX `fk_user_has_pokemon_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_pokemon_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `pokeDB`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_pokemon_pokemon1`
    FOREIGN KEY (`pokemon_id`)
    REFERENCES `pokeDB`.`pokemon` (`pokemon_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pokeDB`.`user_has_team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokeDB`.`user_has_team` (
  `user_id` INT NOT NULL,
  `user_collection_id` INT NOT NULL,
  `pokemon_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `user_collection_id`, `pokemon_id`),
  INDEX `fk_user_has_user_collection_user_collection1_idx` (`user_collection_id` ASC, `pokemon_id` ASC) VISIBLE,
  INDEX `fk_user_has_user_collection_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_user_collection_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `pokeDB`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_user_collection_user_collection1`
    FOREIGN KEY (`user_collection_id` , `pokemon_id`)
    REFERENCES `pokeDB`.`user_collection` (`user_id` , `pokemon_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pokeDB`.`pokemon_has_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokeDB`.`pokemon_has_type` (
  `pokemon_id` INT NOT NULL,
  `pokemon_type` ENUM('bug', 'dragon', 'electric', 'fighting', 'fire', 'flying', 'ghost', 'grass', 'ground', 'ice', 'normal', 'poison', 'psychic', 'rock', 'water') NOT NULL,
  PRIMARY KEY (`pokemon_id`, `pokemon_type`),
  INDEX `fk_pokemon_has_pokemon_type_pokemon_type1_idx` (`pokemon_type` ASC) VISIBLE,
  INDEX `fk_pokemon_has_pokemon_type_pokemon1_idx` (`pokemon_id` ASC) VISIBLE,
  CONSTRAINT `fk_pokemon_has_pokemon_type_pokemon1`
    FOREIGN KEY (`pokemon_id`)
    REFERENCES `pokeDB`.`pokemon` (`pokemon_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pokemon_has_pokemon_type_pokemon_type1`
    FOREIGN KEY (`pokemon_type`)
    REFERENCES `pokeDB`.`pokemon_type` (`type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


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
insert into pokemon (pokemon_id, name, health, attack, defense, speed) values
(1, 'Bulbasaur', 100, 10, 10, 10),
(2, 'Ivysaur', 100, 10, 10, 10),
(3, 'Venusaur', 100, 10, 10, 10),
(4, 'Charmander', 100, 10, 10, 10),
(5, 'Charmeleon', 100, 10, 10, 10),
(6, 'Charizard', 100, 10, 10, 10),
(7, 'Squirtle', 100, 10, 10, 10),
(8, 'Wartortle', 100, 10, 10, 10),
(9, 'Blastoise', 100, 10, 10, 10),
(25, 'Pikachu', 100, 10, 10, 10),
(26, 'Raichu', 100, 10, 10, 10);

-- Insert some pokemon for the Trainer Ash to own
insert into user_collection (user_id, pokemon_id, nickname) values
((SELECT user_id FROM user WHERE user_info_email LIKE 'ash@trainer'), 25, 'ElectroRat');

-- some select commands to test and use in the database loader

select * from user;
select * from user_info;

select is_professor, email, password, user_id from user, user_info where user.user_info_email like user_info.email;
select email, username, login_bonus, win_count, loss_count from user_info where email like 'ash@trainer';
select pokemon_id, nickname from user_collection where user_id = (select user.user_id from user where user_info_email like 'ash@trainer');

select * from user_collection;
select user_collection.pokemon_id, nickname from user_has_team, user_collection where user_has_team.user_id = user_collection.user_id and user_collection.user_id = 2;

SELECT user_id FROM user WHERE user_info_email LIKE 'ash@trainer';
SELECT win_count, loss_count FROM user_info WHERE email LIKE 'ash@trainer';