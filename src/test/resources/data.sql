INSERT INTO Skills (name)
VALUES ('Java'),
       ('SQL'),
       ('HTML');

INSERT INTO Profiles (name, role, username, password, email)
VALUES ('Alice', 'OWNER', 'alice', 'password', 'alice@test.dk'),
       ('Bob', 'JUNIOR', 'bob', 'password', 'bob@test.dk');

INSERT INTO Profiles_Skills (profile_id, skill_id)
VALUES (1, 1),
       (1, 2),
       (2, 3);

INSERT INTO Projects (
    name,
    description,
    hourlyRate,
    startDate,
    endDate,
    estimatedDeadline,
    finalPrice,
    status
)
VALUES
    (
        'Project1',
        'First project',
        500.00,
        '2026-01-01',
        '2026-02-01',
        '2026-02-05',
        50000.00,
        'IN_PROGRESS'
    ),
    (
        'Project2',
        'Second project',
        600.00,
        '2026-03-01',
        '2026-03-20',
        '2026-03-25',
        30000.00,
        'TODO'
    );

INSERT INTO Profiles_Projects (profile_id, project_id)
VALUES (1, 1),
       (2, 2);

INSERT INTO Project_Skills (project_id, skill_id, quantity)
VALUES (1, 1, 2),
       (1, 2, 1),
       (2, 3, 1);

INSERT INTO Tasks (
    name,
    description,
    status,
    startDate,
    endDate,
    project_id
)
VALUES
    (
        'Task 1',
        'First task',
        'IN_PROGRESS',
        '2026-01-01',
        '2026-01-10',
        1
    ),
    (
        'Task 2',
        'Second task',
        'DONE',
        '2026-01-11',
        '2026-01-15',
        1
    ),
    (
        'Task 3',
        'Third task',
        'TODO',
        '2026-03-01',
        '2026-03-10',
        2
    );

INSERT INTO Tasks_Skills (task_id, skill_id, quantity)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 1);

INSERT INTO Sub_Tasks (
    name,
    description,
    duration,
    status,
    startDate,
    endDate,
    task_id,
    profile_id
)
VALUES
    (
        'SubTask 1',
        'First subtask',
        5.00,
        'IN_PROGRESS',
        '2026-01-01',
        '2026-01-03',
        1,
        1
    ),
    (
        'SubTask 2',
        'Second subtask',
        3.00,
        'DONE',
        '2026-01-04',
        '2026-01-05',
        1,
        1
    ),
    (
        'SubTask 3',
        'Third subtask',
        4.00,
        'TODO',
        '2026-03-01',
        '2026-03-03',
        3,
        2
    );

INSERT INTO Sub_Tasks_Skills (sub_task_id, skill_id, quantity)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 1);

INSERT INTO Project_Templates (name, description, hourlyRate, estimatedDeadline)

VALUES
    (
        'Betalingsløsning',
        'Standard betalingsløsning',
        500.00,
        '2026-03-31'
    ),
    (
        'Webshop',
        'Standard webshop',
        600.00,
        '2026-06-01'
    );

INSERT INTO Task_Templates (name, description, project_template_id)
VALUES ('Frontend', 'Lav frontend',1),
       ('Backend', 'Lav backend',  1),
       ('Database', 'Lav database',  2);

INSERT INTO Sub_Task_Templates (name, description, duration, task_template_id)
VALUES ('Design', 'Lav design', 3.00, 1),
       ('Implementer', 'Implementer frontend', 7.00, 1),
       ('API', 'Lav API', 20.00, 2);