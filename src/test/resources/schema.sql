DROP TABLE IF EXISTS Sub_Tasks_Skills;
DROP TABLE IF EXISTS Profiles_Sub_Tasks;
DROP TABLE IF EXISTS Sub_Tasks;
DROP TABLE IF EXISTS Tasks_Skills;
DROP TABLE IF EXISTS Profiles_Tasks;
DROP TABLE IF EXISTS Tasks;
DROP TABLE IF EXISTS Project_Skills;
DROP TABLE IF EXISTS Profiles_Projects;
DROP TABLE IF EXISTS Projects;
DROP TABLE IF EXISTS Profiles_Skills;
DROP TABLE IF EXISTS Profiles;
DROP TABLE IF EXISTS Skills;
DROP TABLE IF EXISTS Sub_Task_Templates;
DROP TABLE IF EXISTS Task_Templates;
DROP TABLE IF EXISTS Project_Templates;

CREATE TABLE Skills
(
    id   INT          NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Profiles
(
    id       INT          NOT NULL AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Profiles_Skills
(
    profile_id INT NOT NULL,
    skill_id   INT NOT NULL,
    PRIMARY KEY (profile_id, skill_id),
    FOREIGN KEY (profile_id) REFERENCES Profiles (id),
    FOREIGN KEY (skill_id) REFERENCES Skills (id)
);

CREATE TABLE Projects
(
    id                INT            NOT NULL AUTO_INCREMENT,
    name              VARCHAR(255)   NOT NULL,
    description       VARCHAR(255),
    hourlyRate        DECIMAL(10, 2) NOT NULL,
    startDate         DATE,
    endDate           DATE,
    estimatedDeadline DATE,
    finalPrice        DECIMAL(10, 2),
    status            VARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TABLE Profiles_Projects
(
    profile_id INT NOT NULL,
    project_id INT NOT NULL,
    PRIMARY KEY (profile_id, project_id),
    FOREIGN KEY (profile_id) REFERENCES Profiles (id),
    FOREIGN KEY (project_id) REFERENCES Projects (id)
);

CREATE TABLE Project_Skills
(
    project_id INT NOT NULL,
    skill_id   INT NOT NULL,
    quantity   INT,
    PRIMARY KEY (project_id, skill_id),
    FOREIGN KEY (project_id) REFERENCES Projects (id),
    FOREIGN KEY (skill_id) REFERENCES Skills (id)
);

CREATE TABLE Tasks
(
    id          INT NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255),
    description VARCHAR(255),
    status      VARCHAR(50),
    startDate   DATE,
    endDate     DATE,
    project_id  INT,
    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES Projects (id)
);

CREATE TABLE Profiles_Tasks
(
    profile_id INT NOT NULL,
    task_id    INT NOT NULL,
    PRIMARY KEY (profile_id, task_id),
    FOREIGN KEY (profile_id) REFERENCES Profiles (id),
    FOREIGN KEY (task_id) REFERENCES Tasks (id)
);

CREATE TABLE Tasks_Skills
(
    task_id  INT NOT NULL,
    skill_id INT NOT NULL,
    quantity INT,
    PRIMARY KEY (task_id, skill_id),
    FOREIGN KEY (task_id) REFERENCES Tasks (id),
    FOREIGN KEY (skill_id) REFERENCES Skills (id)
);

CREATE TABLE Sub_Tasks
(
    id          INT NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255),
    description VARCHAR(255),
    duration    INT,
    status      VARCHAR(50),
    startDate   DATE,
    endDate     DATE,
    task_id     INT,
    PRIMARY KEY (id),
    FOREIGN KEY (task_id) REFERENCES Tasks (id)
);

CREATE TABLE Profiles_Sub_Tasks
(
    profile_id  INT NOT NULL,
    sub_task_id INT NOT NULL,
    PRIMARY KEY (profile_id, sub_task_id),
    FOREIGN KEY (profile_id) REFERENCES Profiles (id),
    FOREIGN KEY (sub_task_id) REFERENCES Sub_Tasks (id)
);

CREATE TABLE Sub_Tasks_Skills
(
    sub_task_id INT NOT NULL,
    skill_id    INT NOT NULL,
    quantity    INT,
    PRIMARY KEY (sub_task_id, skill_id),
    FOREIGN KEY (sub_task_id) REFERENCES Sub_Tasks (id),
    FOREIGN KEY (skill_id) REFERENCES Skills (id)
);

CREATE TABLE Project_Templates
(
    id                    INT            NOT NULL AUTO_INCREMENT,
    name                  VARCHAR(255)   NOT NULL,
    description           VARCHAR(255),
    hourlyRate            DECIMAL(10, 2),
    estimatedDeadlineDays INT,
    PRIMARY KEY (id)
);

CREATE TABLE Task_Templates
(
    id                  INT          NOT NULL AUTO_INCREMENT,
    name                VARCHAR(255),
    description         VARCHAR(255),
    duration            DECIMAL(5, 2),
    project_template_id INT          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (project_template_id) REFERENCES Project_Templates (id)
);

CREATE TABLE Sub_Task_Templates
(
    id               INT          NOT NULL AUTO_INCREMENT,
    name             VARCHAR(255),
    description      VARCHAR(255),
    duration         DECIMAL(5, 2),
    task_template_id INT          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (task_template_id) REFERENCES Task_Templates (id)
);