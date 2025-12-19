# Student Management System

A Spring Boot Web Application for managing student records, featuring a responsive UI and Excel export.

## 🚀 Deployment (Live Demo)

This project includes Docker configuration for easy deployment to cloud platforms like **Render**, **Railway**, or **Fly.io**.

### Option 1: Run Locally with Docker
If you have Docker installed, you can start the entire system (App + Database) with one command:
```bash
docker-compose up --build
```
The application will be available at: `http://localhost:8081`

### Option 2: Deploy to Cloud (Free Way)

To make it accessible to everyone, you need two things: **Hosting** (for the Java App) and a **Database** (for the data).

#### Step 1: Get a Free MySQL Database
Since your local MySQL won't work online, you need a cloud database.
1.  Sign up for [TiDB Cloud](https://tidbcloud.com/) or [Aiven](https://aiven.io/) (both offer free MySQL-compatible tiers).
2.  Create a cluster/service.
3.  **Copy** these details from their dashboard:
    *   Host (e.g., `gateway01.region.tidbcloud.com`)
    *   Port (e.g., `4000` or `3306`)
    *   Username
    *   Password
    *   Database Name

#### Step 2: Deploy App to Render.com (Automated)

We will use a **Render Blueprint** (`render.yaml`) to automate the setup.

1.  Sign up at [Render.com](https://render.com).
2.  Click **New +** -> **Blueprint**.
3.  Connect your GitHub and select this repository (`Student-Management-System`).
4.  Render will find the `render.yaml` file.
5.  Click **Apply**.
6.  It will ask you for the **Environment Variables** (the database details you got in Step 1):
    *   `SPRING_DATASOURCE_URL`
    *   `SPRING_DATASOURCE_USERNAME`
    *   `SPRING_DATASOURCE_PASSWORD`
7.  Click **Approve**. Render will deploy your app automatically.

### Option 3: Deploy to Railway (Easiest for Database)

**Railway** is likely the "best" option if you want the App and Database to be setup together without configuration headaches.

1.  Sign up at [Railway.app](https://railway.app/).
2.  Click **New Project** -> **Deploy from GitHub repo**.
3.  Select this repository (`Student-Management-System`).
4.  Once the App is added, click **New** (in the same project) -> **Database** -> **MySQL**.
5.  Railway will create a MySQL service.
6.  Click on your **Java App** service -> **Variables**.
7.  Add the same variables (`SPRING_DATASOURCE_URL`, etc.) but use the *internal* values provided by the MySQL service (available in the MySQL service "Connect" tab).
    *   *Tip: In Railway, you can often reference other services dynamically.*

---

## Features

### 1. **Add Student**
- Add a new student with unique name and ID.
- Input validation ensures valid data for name, ID, age, GPA, year, and department.

### 2. **Remove Student**
- Remove a student by their unique ID.

### 3. **Update Student**
- Update a student's information (name, age, GPA, year, or department) using their ID.

### 4. **Search Student**
- Search for a student by their ID.

### 5. **List and Sort Students**
- List all students sorted by GPA, ID, Name, Year, or Department.

### 6. **Filter Students**
- Filter students by Age, GPA, Year, or Department.

### 7. **Count Total Students**
- Display the total number of students in the system.

### 8. **Calculate Average GPA**
- Calculate and display the average GPA of all students.

### 9. **Display Top 5 Students**
- Display the top 5 performing students based on GPA.

### 10. **Display Failing Students**
- Display students with a GPA less than 2.0.

### 11. **Generate Summary**
- Generate a summary including:
    - Average GPA
    - Total number of students
    - Top 5 performing students
    - Failing students

---

## UML Diagram

For a detailed UML class diagram of the project, refer to [UML_Diagram.md](https://github.com/Mohammed-3tef/Java-Projects-Collections/blob/main/Student%20Management%20System/UML%20Diagram.md).

---

## Input Validation

The system ensures all inputs are valid:
- **Name**: Only letters and spaces.
- **ID**: Unique positive integer.
- **Age**: Valid age between 0 and 100.
- **GPA**: Valid GPA between 0.0 and 4.0.
- **Year**: Must be one of `First`, `Second`, `Third`, or `Fourth`.
- **Department**: Must be one of `CS`, `IS`, `AI`, `IT`, or `DS`.

---

## Code Structure

### Classes
1. **`Student`**:
    - Represents a student with attributes: `name`, `ID`, `age`, `GPA`, `year`, and `department`.

2. **`StudentSystem`**:
    - Manages a list of students and provides methods for adding, removing, updating, searching, sorting, filtering, and generating summaries.

### Methods
- **Input Validation**:
    - `inputValidName`, `inputValidID`, `inputValidAge`, `inputValidGPA`, `inputValidYear`, `inputValidDepartment`, `inputValidChoice`.
- **Student Operations**:
    - `addStudent`, `removeStudentByID`, `updateStudentByID`, `searchByID`, `listAndSortAllStudents`, `filterByAge`, `filterByGPA`, `filterByYear`, `filterByDepartment`, `countTotalStudents`, `calculateAverageGPA`, `displayTop5`, `displayFailingStudents`, `countStudentsByYear`, `generateSummary`.

---

## Example Usage

### Adding a Student
1. Choose option `1` from the menu.
2. Enter the student's name, ID, age, GPA, year, and department.
3. The student will be added to the system.

### Updating a Student
1. Choose option `3` from the menu.
2. Enter the student's ID.
3. Choose the attribute to update (name, age, GPA, year, or department).
4. Enter the new value.

### Generating a Summary
1. Choose option `11` from the menu.
2. The system will display:
    - Average GPA
    - Total number of students
    - Top 5 performing students
    - Failing students

---

## Acknowledgments

- Thanks to the Java community for providing excellent resources and libraries.
- Inspired by real-world student management systems.

---

## Enjoy managing your students with ease! 🚀
