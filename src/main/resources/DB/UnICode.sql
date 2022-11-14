-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`User` (
  `uid` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NULL,
  `name` VARCHAR(45) NULL,
  `profile` VARCHAR(45) NULL,
  `created_time` TIMESTAMP NULL,
  `access_img` VARCHAR(45) NULL,
  `access` TINYINT NULL,
  `score` INT NULL,
  `authority` TINYINT NULL,
  `email` VARCHAR(45) NULL,
  `github` VARCHAR(45) NULL,
  PRIMARY KEY (`uid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Board` (
  `bid` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `content` VARCHAR(1000) NULL,
  `created_time` TIMESTAMP NOT NULL,
  `updated_time` TIMESTAMP NULL,
  `uid` VARCHAR(45) NOT NULL,
  `main_img_url` VARCHAR(100) NULL,
  `hit` INT NULL DEFAULT 0,
  `like` INT NULL DEFAULT 0,
  `open` TINYINT NULL,
  `max_member` INT NULL,
  PRIMARY KEY (`bid`),
  INDEX `USER_BOARD_id_wirtre_idx` (`uid` ASC) VISIBLE,
  CONSTRAINT `USER_BOARD_ID_WRITER`
    FOREIGN KEY (`uid`)
    REFERENCES `mydb`.`User` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Project` (
  `pid` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `content` VARCHAR(1000) NULL,
  `created_time` TIMESTAMP NOT NULL,
  `updated_time` TIMESTAMP NULL,
  `url` VARCHAR(45) NULL,
  `hit` INT NULL DEFAULT 0,
  `like` INT NULL DEFAULT 0,
  `img` VARCHAR(45) NULL,
  PRIMARY KEY (`pid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`language`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`language` (
  `lid` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`lid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`B_Develop`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`B_Develop` (
  `bid` INT NOT NULL,
  `lid` INT NOT NULL,
  PRIMARY KEY (`bid`, `lid`),
  INDEX `LANGUAGE_DEVELOP_LID_idx` (`lid` ASC) VISIBLE,
  CONSTRAINT `BOARD_DEVELOP_BID`
    FOREIGN KEY (`bid`)
    REFERENCES `mydb`.`Board` (`bid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `LANGUAGE_DEVELOP_LID`
    FOREIGN KEY (`lid`)
    REFERENCES `mydb`.`language` (`lid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`comment` (
  `cid` INT NOT NULL AUTO_INCREMENT,
  `comment` VARCHAR(45) NULL,
  `bid` INT NULL,
  `class` INT NULL,
  `created_time` TIMESTAMP NULL,
  `parent_cid` INT NULL,
  `id` VARCHAR(45) NULL,
  PRIMARY KEY (`cid`),
  INDEX `GROUP_NUM_CID_idx` (`parent_cid` ASC) VISIBLE,
  INDEX `BOARD_COMMENT_BID_idx` (`bid` ASC) VISIBLE,
  INDEX `USER_COMMENT_ID_idx` (`id` ASC) VISIBLE,
  CONSTRAINT `GROUP_NUM_CID`
    FOREIGN KEY (`parent_cid`)
    REFERENCES `mydb`.`comment` (`cid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `BOARD_COMMENT_BID`
    FOREIGN KEY (`bid`)
    REFERENCES `mydb`.`Board` (`bid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `USER_COMMENT_ID`
    FOREIGN KEY (`id`)
    REFERENCES `mydb`.`User` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Board_Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Board_Category` (
  `bid` INT NOT NULL,
  `main_class` INT NULL,
  `middle_class` INT NULL,
  PRIMARY KEY (`bid`),
  CONSTRAINT `BOARD_CATEGORY_BID`
    FOREIGN KEY (`bid`)
    REFERENCES `mydb`.`Board` (`bid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`P_Develop`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`P_Develop` (
  `pid` INT NOT NULL,
  `lid` INT NOT NULL,
  PRIMARY KEY (`lid`, `pid`),
  INDEX `LANGUAGE_DEVELOP_LID_idx` (`lid` ASC) VISIBLE,
  INDEX `PROJECT_DEVELOP_PID_idx` (`pid` ASC) VISIBLE,
  CONSTRAINT `PROJECT_DEVELOP_PID`
    FOREIGN KEY (`pid`)
    REFERENCES `mydb`.`Project` (`pid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `LANGUAGE_DEVELOP_LID0`
    FOREIGN KEY (`lid`)
    REFERENCES `mydb`.`language` (`lid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`file_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`file_info` (
  `fid` INT NOT NULL AUTO_INCREMENT,
  `bid` INT NULL,
  `save_folder` VARCHAR(45) NULL,
  `origin_file` VARCHAR(45) NULL,
  `save_file` VARCHAR(45) NULL,
  PRIMARY KEY (`fid`),
  INDEX `FILE_BOARD_BID_idx` (`bid` ASC) VISIBLE,
  CONSTRAINT `FILE_BOARD_BID`
    FOREIGN KEY (`bid`)
    REFERENCES `mydb`.`Board` (`bid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ProjectMember`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ProjectMember` (
  `uid` VARCHAR(45) NOT NULL,
  `pid` INT NOT NULL,
  PRIMARY KEY (`uid`, `pid`),
  INDEX `fk_ProjectMember_User1_idx` (`uid` ASC) VISIBLE,
  INDEX `fk_ProjectMember_Project1_idx` (`pid` ASC) VISIBLE,
  CONSTRAINT `fk_ProjectMember_User1`
    FOREIGN KEY (`uid`)
    REFERENCES `mydb`.`User` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ProjectMember_Project1`
    FOREIGN KEY (`pid`)
    REFERENCES `mydb`.`Project` (`pid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `mydb` ;

-- -----------------------------------------------------
-- Placeholder table for view `mydb`.`view1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`view1` (`id` INT);

-- -----------------------------------------------------
-- View `mydb`.`view1`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`view1`;
USE `mydb`;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
