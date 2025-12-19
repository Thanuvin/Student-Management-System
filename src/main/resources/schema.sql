CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gpa DOUBLE NOT NULL,
    year VARCHAR(50),
    department VARCHAR(100)
);
