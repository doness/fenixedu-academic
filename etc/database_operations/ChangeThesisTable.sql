ALTER TABLE `THESIS` 
	MODIFY COLUMN `CREATION`	     DATETIME,
	MODIFY COLUMN `SUBMISSION`   DATETIME,
	MODIFY COLUMN `APPROVAL`     DATETIME,
	MODIFY COLUMN `CONFIRMATION` DATETIME,
	MODIFY COLUMN `EVALUATION`   DATETIME,
	MODIFY COLUMN `DISCUSSED`    DATETIME AFTER `EVALUATION`;

ALTER TABLE `THESIS`
	MODIFY COLUMN `MARK` INTEGER;

ALTER TABLE `THESIS`
	ADD COLUMN `ORIENTATOR_CREDITS_DISTRIBUTION` INTEGER;

UPDATE `THESIS_EVALUATION_PARTICIPANT`
	SET `KEY_ROOT_DOMAIN_OBJECT` = 1;

ALTER TABLE `THESIS`
	ADD COLUMN `VISIBILITY` VARCHAR(255),
	ADD COLUMN `DECLARATION_ACCEPTED` BOOLEAN,
	ADD COLUMN `DECLARATION_ACCEPTED_TIME` DATETIME;

ALTER TABLE `THESIS_EVALUATION_PARTICIPANT`
	CHANGE COLUMN `KEY_CATEGORY` `CATEGORY` VARCHAR(255);

ALTER TABLE `THESIS_EVALUATION_PARTICIPANT`
	ADD COLUMN `PERSON_NAME` VARCHAR(255);

UPDATE THESIS_EVALUATION_PARTICIPANT TEP, PARTY P 
	SET TEP.PERSON_NAME = P.NAME 
	WHERE TEP.KEY_PERSON = P.ID_INTERNAL;

ALTER TABLE `THESIS` 
	ADD COLUMN `DOCUMENTS_AVAILABLE_AFTER` DATETIME;
