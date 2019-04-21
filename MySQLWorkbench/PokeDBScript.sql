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
-- Table `pokeDB`.`user_stats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokeDB`.`user_stats` (
  `user_id` INT NOT NULL,
  `login_bonus` DATE NULL,
  `win_count` INT NULL,
  `loss_count` INT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pokeDB`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pokeDB`.`user` (
  `email` VARCHAR(30) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `is_professor` TINYINT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`email`, `user_id`),
  INDEX `fk_user_user_stats_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_user_stats`
    FOREIGN KEY (`user_id`)
    REFERENCES `pokeDB`.`user_stats` (`user_id`)
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
