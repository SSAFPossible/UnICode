-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema UnICode
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema UnICode
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `UnICode` DEFAULT CHARACTER SET utf8 ;
USE `UnICode` ;

-- -----------------------------------------------------
-- Table `UnICode`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UnICode`.`user` (
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
-- Table `UnICode`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UnICode`.`board` (
  `bid` INT NOT NULL AUTO_INCREMENT,
  `bcid` VARCHAR(45) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `content` VARCHAR(1000) NULL,
  `created_time` TIMESTAMP NOT NULL,
  `updated_time` TIMESTAMP NULL,
  `uid` VARCHAR(45) NOT NULL,
  `hit` INT NULL DEFAULT 0,
  `like_cnt` INT NULL DEFAULT 0,
  `open` TINYINT NULL,
  `max_member` INT NULL,
  PRIMARY KEY (`bid`),
  INDEX `user_board_id_wirtre_idx` (`uid` ASC) VISIBLE,
  CONSTRAINT `user_board_ID_WRITER`
    FOREIGN KEY (`uid`)
    REFERENCES `UnICode`.`user` (`uid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `UnICode`.`board_like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UnICode`.`board_like` (
  `bid` INT NOT NULL,
  `uid` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`bid`, `uid`),
  FOREIGN KEY (`bid`) REFERENCES `UnICode`.`board` (`bid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`uid`) REFERENCES `UnICode`.`user` (`uid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `UnICode`.`project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UnICode`.`project` (
  `pid` INT NOT NULL AUTO_INCREMENT,
  `bid` INT NOT NULL,
  `url` VARCHAR(45) NULL,
  PRIMARY KEY (`pid`),
  FOREIGN KEY (`bid`) REFERENCES `UnICode`.`board` (`bid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `UnICode`.`language`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UnICode`.`language` (
  `lid` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`lid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `UnICode`.`b_develop`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UnICode`.`b_develop` (
  `bid` INT NOT NULL,
  `lid` INT NOT NULL,
  PRIMARY KEY (`bid`, `lid`),
  INDEX `LANGUAGE_DEVELOP_LID_idx` (`lid` ASC) VISIBLE,
  CONSTRAINT `board_DEVELOP_BID`
    FOREIGN KEY (`bid`)
    REFERENCES `UnICode`.`board` (`bid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `LANGUAGE_DEVELOP_LID`
    FOREIGN KEY (`lid`)
    REFERENCES `UnICode`.`language` (`lid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `UnICode`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UnICode`.`comment` (
  `cid` INT NOT NULL AUTO_INCREMENT,
  `comment` VARCHAR(45) NULL,
  `bid` INT NULL,
  `created_time` TIMESTAMP NULL,
  `updated_time` TIMESTAMP NULL,
  `parent_cid` INT DEFAULT NULL,
  `uid` VARCHAR(45) NULL,
  `deleted` TINYINT DEFAULT 0,
  PRIMARY KEY (`cid`),
  INDEX `GROUP_NUM_CID_idx` (`parent_cid` ASC) VISIBLE,
  INDEX `board_COMMENT_BID_idx` (`bid` ASC) VISIBLE,
  INDEX `user_COMMENT_ID_idx` (`uid` ASC) VISIBLE,
  CONSTRAINT `comment_COMMENT_PARENT_CID`
    FOREIGN KEY (`parent_cid`)
    REFERENCES `UnICode`.`comment` (`cid`),
  CONSTRAINT `board_COMMENT_BID`
    FOREIGN KEY (`bid`)
    REFERENCES `UnICode`.`board` (`bid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `user_COMMENT_UID`
    FOREIGN KEY (`uid`)
    REFERENCES `UnICode`.`user` (`uid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `UnICode`.`p_develop`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UnICode`.`p_develop` (
  `pid` INT NOT NULL,
  `lid` INT NOT NULL,
  PRIMARY KEY (`lid`, `pid`),
  INDEX `LANGUAGE_DEVELOP_LID_idx` (`lid` ASC) VISIBLE,
  INDEX `project_DEVELOP_PID_idx` (`pid` ASC) VISIBLE,
  CONSTRAINT `project_DEVELOP_PID`
    FOREIGN KEY (`pid`)
    REFERENCES `UnICode`.`project` (`pid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `LANGUAGE_DEVELOP_LID0`
    FOREIGN KEY (`lid`)
    REFERENCES `UnICode`.`language` (`lid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `UnICode`.`file_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UnICode`.`file_info` (
  `fid` INT NOT NULL AUTO_INCREMENT,
  `bid` INT NULL,
  `type` VARCHAR(10) NULL,
  `save_folder` VARCHAR(45) NULL,
  `origin_file` VARCHAR(45) NULL,
  `save_file` VARCHAR(45) NULL,
  PRIMARY KEY (`fid`),
  INDEX `FILE_board_BID_idx` (`bid` ASC) VISIBLE,
  CONSTRAINT `FILE_board_BID`
    FOREIGN KEY (`bid`)
    REFERENCES `UnICode`.`board` (`bid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `UnICode`.`file_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UnICode`.`project_main_img` (
  `pmid` INT NOT NULL AUTO_INCREMENT,
  `pid` INT NULL,
  `save_folder` VARCHAR(45) NULL,
  `origin_file` VARCHAR(45) NULL,
  `save_file` VARCHAR(45) NULL,
  PRIMARY KEY (`pmid`),
    FOREIGN KEY (`pid`)
    REFERENCES `UnICode`.`project` (`pid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `UnICode`.`project_member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UnICode`.`project_member` (
  `uid` VARCHAR(45) NOT NULL,
  `pid` INT NOT NULL,
  PRIMARY KEY (`uid`, `pid`),
  INDEX `fk_project_member_user1_idx` (`uid` ASC) VISIBLE,
  INDEX `fk_project_member_project1_idx` (`pid` ASC) VISIBLE,
  CONSTRAINT `fk_project_member_user1`
    FOREIGN KEY (`uid`)
    REFERENCES `UnICode`.`user` (`uid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_project_member_project1`
    FOREIGN KEY (`pid`)
    REFERENCES `UnICode`.`project` (`pid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `UnICode` ;

-- -----------------------------------------------------
-- Placeholder table for view `UnICode`.`view1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UnICode`.`view1` (`id` INT);

-- -----------------------------------------------------
-- View `UnICode`.`view1`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `UnICode`.`view1`;
USE `UnICode`;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;