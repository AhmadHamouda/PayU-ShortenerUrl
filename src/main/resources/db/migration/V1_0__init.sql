-- -----------------------------------------------------
-- Schema shorten_urls
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `shorten_urls`
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_bin;
USE `shorten_urls`;

-- -----------------------------------------------------
-- Table `shorten_urls`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shorten_urls`.`users` (
  `id`       INT(11)          NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50)
             COLLATE utf8_bin NOT NULL,
  `password` VARCHAR(256)
             COLLATE utf8_bin NOT NULL,
  `role`     VARCHAR(50)
             COLLATE utf8_bin NOT NULL,
  `active`   INT(1)           NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;

-- -----------------------------------------------------
-- Table `shorten_urls`.`urls`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shorten_urls`.`urls` (
  `id`            INT(11)          NOT NULL AUTO_INCREMENT,
  `hit_count`     INT(11)                   DEFAULT 0,
  `shorten_url`   VARCHAR(50)
                  COLLATE utf8_bin NOT NULL,
  `url`           VARCHAR(1000)
                  COLLATE utf8_bin NOT NULL,
  `creation_date` DATE             NOT NULL,
  `user_id`       INT(11)          NULL,
  INDEX `fk_url_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_url_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `users` (`id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;


