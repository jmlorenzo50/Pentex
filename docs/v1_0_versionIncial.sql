SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema testtomcat
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `testtomcat` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `testtomcat` ;

-- -----------------------------------------------------
-- Table `testtomcat`.`tt_aplications`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `testtomcat`.`tt_aplications` (
  `id_app` VARCHAR(45) NOT NULL,
  `id_secret` VARCHAR(45) NULL,
  `ds_app` VARCHAR(45) NULL,
  PRIMARY KEY (`id_app`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `testtomcat`.`tt_users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `testtomcat`.`tt_users` (
  `id_user` VARCHAR(10) NOT NULL,
  `ds_user` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  PRIMARY KEY (`id_user`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `testtomcat`.`tt_tokens`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `testtomcat`.`tt_tokens` (
  `id_app` VARCHAR(45) NOT NULL,
  `id_user` VARCHAR(10) NOT NULL,
  `token` VARCHAR(128) NULL,
  PRIMARY KEY (`id_app`, `id_user`),
  INDEX `fk_table1_tt_users1_idx` (`id_user` ASC),
  CONSTRAINT `fk_table1_tt_aplications`
    FOREIGN KEY (`id_app`)
    REFERENCES `testtomcat`.`tt_aplications` (`id_app`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_table1_tt_users1`
    FOREIGN KEY (`id_user`)
    REFERENCES `testtomcat`.`tt_users` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
