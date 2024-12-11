drop schema my_question;
create schema IF NOT EXISTS my_question;
use my_question;

CREATE TABLE IF NOT EXISTS department (
  Department_id INT NOT NULL AUTO_INCREMENT,
  Department_name VARCHAR(45) NOT NULL,
  Number_of_lecturers INT NOT NULL,
  Number_of_students INT NOT NULL,
  PRIMARY KEY (`Department_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS lecturers (
  Lecturer_id INT NOT NULL AUTO_INCREMENT,
  lecturer_name VARCHAR(45) NOT NULL,
  departmentID INT NOT NULL,
  number_of_tests INT NULL DEFAULT NULL,
  PRIMARY KEY (`Lecturer_id`),
  INDEX `departmentID_idx` (`departmentID` ASC) VISIBLE,
  CONSTRAINT `departmentID`
    FOREIGN KEY (`departmentID`)
    REFERENCES `department` (`Department_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS questions (
  questionID INT NOT NULL AUTO_INCREMENT,
  question TEXT NOT NULL,
  lecturerID INT NOT NULL,
  typeQuestion INT NOT NULL,
  PRIMARY KEY (`questionID`),
  INDEX `Lecturer_id_idx` (`lecturerID` ASC) VISIBLE,
  CONSTRAINT `Lecturer_id`
    FOREIGN KEY (`lecturerID`)
    REFERENCES `lecturers` (`Lecturer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS exam (
  dateCreated VARCHAR(45) NULL DEFAULT NULL,
  title VARCHAR(45) NOT NULL,
  lecturers_Lecturer_id INT NOT NULL,
  PRIMARY KEY (`lecturers_Lecturer_id`),
  CONSTRAINT `fk_exam_lecturers1`
    FOREIGN KEY (`lecturers_Lecturer_id`)
    REFERENCES `lecturers` (`Lecturer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS open_question (
  questionID INT NOT NULL,
  answer TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`questionID`),
  CONSTRAINT `open_question_ibfk_1`
    FOREIGN KEY (`questionID`)
    REFERENCES `questions` (`questionID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS multiple_choice (
  questionID INT NOT NULL,
  answer TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`questionID`),
  CONSTRAINT `multiple_choice_ibfk_1`
    FOREIGN KEY (`questionID`)
    REFERENCES `my_question`.`questions` (`questionID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

insert into department (Department_id, Department_name , Number_of_lecturers , Number_of_students) values
(1,'Food' , 6, 2000),
(2,'Sciences' , 6, 2000),
(3,'Computers' , 6, 2000),
(4,'Physics' , 6, 2000),
(5,'Excel' , 6, 2000),
(6,'Bad computer' , 6, 2000);

insert into lecturers (Lecturer_id,lecturer_name,departmentID,number_of_tests) values
(1,'Adi',1,0),
(2,'Noam',2,0),
(3,'Avi',3,0),
(4,'Eli',4,0),
(5,'Renana',5,0),
(6,'Avigail',6,0);

-- Insert questions
INSERT INTO questions (questionID, question ,lecturerID ,typeQuestion) VALUES 
('1', 'What type of animal is a panda?', '1', '1' ),
('2', 'What is the chemical symbol for oxygen', '3', '1' ),
('3', 'What is the capital of France', '4', '1' ),
('4', 'What is the process by which plants produce their own food through photosynthesis', '5', '1' ),
('5', 'What is the largest animal on earth, and how does it feed', '6', '0' ),
('6', 'How do animals adapt to changing environments, and what are some examples of animal adaptations?', '3', '0' ),
('7', 'How do different animal species communicate with each other?', '2', '0' ),
('8', 'What is the most intelligent animal, and how do we measure intelligence in animals?', '1', '0' );

-- Insert multiple_choice
INSERT INTO multiple_choice ( questionID, answer ) VALUES 
('1', 
'1. Mammal - true
2. Reptile - false
3. Fish - false
4. Amphibian - false'),
('2',
 '1. O - true
 2. N - false
 3. C - false
 4. H - false' ),
 ('3', 
 '1. Berlin - false
 2. London - false
 3. Paris - true
 4. Rome - false'),
 ('4' , 
 '1. Respiration - false
 2. Digestion - false
 3. Photosynthesis - true
 4. Fertilization - false' );

-- Insert Open quetion
INSERT INTO open_question (questionID, answer) VALUES 
('5', 'The largest animal on earth is the blue whale. It feeds by filtering small organisms, such as krill, out of the water using baleen plates in its mouth.'),
('7', 'Different animal species communicate with each other in various ways, such as through vocalizations, body language, and scent marking.'),
('8', 'It is difficult to definitively say which animal is the most intelligent, as intelligence can be difficult to measure and can vary depending on the criteria used. Some animals that are often considered to be intelligent include dolphins, elephants, chimpanzees, and certain species of birds, such as crows and parrots. - true'),
('6', 'Animals can adapt to changing environments through various means, such as evolving physical or behavioral traits that help them better survive and reproduce in their environment.');
