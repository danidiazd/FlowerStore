-- MySQL Script generated by MySQL Workbench
-- Thu Apr  4 23:39:00 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema nombre_a_cambiar
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `nombre_a_cambiar` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `nombre_a_cambiar` ;
CREATE DATABASE IF NOT EXISTS `nombre_a_cambiar` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `nombre_a_cambiar` ;
-- -----------------------------------------------------
-- Table `nombre_a_cambiar`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nombre_a_cambiar`.`product` ;

CREATE TABLE IF NOT EXISTS `nombre_a_cambiar`.`product` (
  `idproduct` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `quantity` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  `type` ENUM('FLOWER', 'TREE', 'DECORATION') NULL DEFAULT NULL,
  PRIMARY KEY (`idproduct`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `nombre_a_cambiar`.`decoration`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nombre_a_cambiar`.`decoration` ;

CREATE TABLE IF NOT EXISTS `nombre_a_cambiar`.`decoration` (
  `material` VARCHAR(45) NOT NULL,
  `product_idproduct` INT NOT NULL,
  INDEX `fk_decoration_product1_idx` (`product_idproduct` ASC) VISIBLE,
  CONSTRAINT `fk_decoration_product1`
    FOREIGN KEY (`product_idproduct`)
    REFERENCES `nombre_a_cambiar`.`product` (`idproduct`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `nombre_a_cambiar`.`flower`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nombre_a_cambiar`.`flower` ;

CREATE TABLE IF NOT EXISTS `nombre_a_cambiar`.`flower` (
  `color` VARCHAR(45) NOT NULL,
  `product_idproduct` INT NOT NULL,
  INDEX `fk_flower_product1_idx` (`product_idproduct` ASC) VISIBLE,
  CONSTRAINT `fk_flower_product1`
    FOREIGN KEY (`product_idproduct`)
    REFERENCES `nombre_a_cambiar`.`product` (`idproduct`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `nombre_a_cambiar`.`ticket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nombre_a_cambiar`.`ticket` ;

CREATE TABLE IF NOT EXISTS `nombre_a_cambiar`.`ticket` (
  `idticket` INT NOT NULL,
  `date` DATE NULL,
  `totalPrice` FLOAT NULL,
  PRIMARY KEY (`idticket`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `nombre_a_cambiar`.`product_ticket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nombre_a_cambiar`.`product_ticket` ;

CREATE TABLE IF NOT EXISTS `nombre_a_cambiar`.`product_ticket` (
  `idproduct_ticket` INT NOT NULL,
  `amount` SMALLINT(10) NULL,
  `product_idproduct` INT NOT NULL,
  `ticket_idticket` INT NOT NULL,
  PRIMARY KEY (`idproduct_ticket`),
  INDEX `fk_product_ticket_product1_idx` (`product_idproduct` ASC) VISIBLE,
  INDEX `fk_product_ticket_ticket1_idx` (`ticket_idticket` ASC) VISIBLE,
  CONSTRAINT `fk_product_ticket_product1`
    FOREIGN KEY (`product_idproduct`)
    REFERENCES `nombre_a_cambiar`.`product` (`idproduct`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_ticket_ticket1`
    FOREIGN KEY (`ticket_idticket`)
    REFERENCES `nombre_a_cambiar`.`ticket` (`idticket`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `nombre_a_cambiar`.`tree`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nombre_a_cambiar`.`tree` ;

CREATE TABLE IF NOT EXISTS `nombre_a_cambiar`.`tree` (
  `height` DOUBLE NOT NULL,
  `product_idproduct` INT NOT NULL,
  INDEX `fk_tree_product1_idx` (`product_idproduct` ASC) VISIBLE,
  CONSTRAINT `fk_tree_product1`
    FOREIGN KEY (`product_idproduct`)
    REFERENCES `nombre_a_cambiar`.`product` (`idproduct`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
