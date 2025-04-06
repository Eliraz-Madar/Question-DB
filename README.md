# Automated Examination System

## Introduction
Welcome to the **Automated Examination System**, a cutting-edge software designed to simplify the creation and management of exams. This system caters to educators, trainers, HR personnel, and certification administrators by providing an efficient solution for generating assessments, managing questions, and tracking results.

## Table of Contents
- [General Description](#general-description)
- [Features](#features)
- [Entities](#entities)
- [Usage](#usage)
  - [Examples of Use Cases](#examples-of-use-cases)
  - [User Types](#user-types)
- [Installation Instructions](#installation-instructions)
- [Contribution Guidelines](#contribution-guidelines)
- [License](#license)

## General Description
The Automated Examination System is a comprehensive tool that:
- Allows users to create exams manually or automatically.
- Supports open-ended and multiple-choice questions.
- Offers a database for storing, updating, and deleting questions and answers.
- Enables sorting questions in dictionary order.
- Includes functionality to display answers after each question in tests.

This software is ideal for creating assessments in educational settings, employee training programs, and online learning platforms. It provides flexibility and convenience for self-assessments, recruitment tests, and certification exams.

## Features
- **Manual and Automated Test Creation**: Generate tests with customizable formats, including American-style multiple-choice questions.
- **Question Management**: Add, update, delete, and organize questions and answers.
- **Sorting and Display Options**: Sort questions alphabetically and show answers inline during tests.
- **User-Friendly Interface**: Intuitive menus for exam creation and management.

## Entities
- **Department**:
  - `Department_id`
  - `Department_name`
  - `Number_of_lecturers`
  - `Number_of_students`

- **Lecturers**:
  - `Lecturer_id`
  - `Lecturer_name`
  - `Department_id`
  - `Number_of_tests`

- **Questions**:
  - `Question_id`
  - `Question`
  - `Lecturer_id`
  - `Type_question`

- **Open Questions**:
  - `Question_id`
  - `Answer`

- **Multiple Choice Questions**:
  - `Question_id`
  - `Answer`

- **Exams**:
  - `Date_created`
  - `Title`
  - `Lecturer_id`

## Usage
### Examples of Use Cases
1. **Education and Training**: Generate quizzes for classrooms or corporate training.
2. **Recruitment**: Create certification exams or hiring assessments.
3. **Online Learning**: Support e-learning platforms with integrated exam creation tools.

### User Types
The primary users of this system are exam managers who can:
- Manage questions and answers.
- Generate exams manually or randomly.
- Update and delete existing questions.
- Create assessments for various purposes, including recruitment and certification.

## Installation Instructions
1. **Clone the Repository**:
   ```bash
    git clone https://github.com/Eliraz-Madar/Question-DB.git
    cd Question-DB
   ```
2. **Set Up the Database**:
   - Use the provided SQL scripts to create tables and insert sample data.
   - Ensure the database connection is properly configured in the application settings.

3. **Run the Application**:
   - Compile and execute the project using your preferred IDE or command line.

## Contribution Guidelines
We welcome contributions to improve the Automated Examination System! To contribute:
1. Fork this repository.
2. Create a new branch for your changes:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes and push to your branch:
   ```bash
   git commit -m "Description of changes"
   git push origin feature-name
   ```
4. Submit a pull request for review.

## License
This project is licensed under the MIT License. See the LICENSE file for details.

---
Thank you for using the Automated Examination System. We hope it simplifies your exam creation and management needs!

