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
-- Schema floresPaquitaSL
-- -----------------------------------------------------
CREATE DATABASE IF NOT EXISTS `floresPaquitaSL` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `floresPaquitaSL` ;

CREATE SCHEMA IF NOT EXISTS `floresPaquitaSL` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `floresPaquitaSL` ;

-- -----------------------------------------------------
-- Table `floresPaquitaSL`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `floresPaquitaSL`.`product` ;

CREATE TABLE IF NOT EXISTS `floresPaquitaSL`.`product` (
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
-- Table `floresPaquitaSL`.`decoration`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `floresPaquitaSL`.`decoration` ;

CREATE TABLE IF NOT EXISTS `floresPaquitaSL`.`decoration` (
  `material` VARCHAR(45) NOT NULL,
  `product_idproduct` INT NOT NULL,
  INDEX `fk_decoration_product1_idx` (`product_idproduct` ASC) VISIBLE,
    FOREIGN KEY (`product_idproduct`)
    REFERENCES `floresPaquitaSL`.`product` (`idproduct`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `floresPaquitaSL`.`flower`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `floresPaquitaSL`.`flower` ;

CREATE TABLE IF NOT EXISTS `floresPaquitaSL`.`flower` (
  `color` VARCHAR(45) NOT NULL,
  `product_idproduct` INT NOT NULL,
  INDEX `fk_flower_product1_idx` (`product_idproduct` ASC) VISIBLE,
    FOREIGN KEY (`product_idproduct`)
    REFERENCES `floresPaquitaSL`.`product` (`idproduct`) ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `floresPaquitaSL`.`ticket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `floresPaquitaSL`.`ticket` ;

CREATE TABLE IF NOT EXISTS `floresPaquitaSL`.`ticket` (
  `idticket` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NULL,
  `totalPrice` FLOAT NULL,
  PRIMARY KEY (`idticket`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `floresPaquitaSL`.`product_ticket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `floresPaquitaSL`.`product_ticket` ;

CREATE TABLE IF NOT EXISTS `floresPaquitaSL`.`product_ticket` (
  `idproduct_ticket` INT NOT NULL AUTO_INCREMENT,
  `amount` SMALLINT(10) NULL,
  `product_idproduct` INT NOT NULL,
  `ticket_idticket` INT NOT NULL,
  PRIMARY KEY (`idproduct_ticket`),
  INDEX `fk_product_ticket_product1_idx` (`product_idproduct` ASC) VISIBLE,
  INDEX `fk_product_ticket_ticket1_idx` (`ticket_idticket` ASC) VISIBLE,
    FOREIGN KEY (`product_idproduct`)
    REFERENCES `floresPaquitaSL`.`product` (`idproduct`)
    ON DELETE CASCADE,
    FOREIGN KEY (`ticket_idticket`)
    REFERENCES `floresPaquitaSL`.`ticket` (`idticket`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `floresPaquitaSL`.`tree`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `floresPaquitaSL`.`tree` ;

CREATE TABLE IF NOT EXISTS `floresPaquitaSL`.`tree` (
  `height` DOUBLE NOT NULL,
  `product_idproduct` INT NOT NULL,
  INDEX `fk_tree_product1_idx` (`product_idproduct` ASC) VISIBLE,
    FOREIGN KEY (`product_idproduct`)
    REFERENCES `floresPaquitaSL`.`product` (`idproduct`)ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;