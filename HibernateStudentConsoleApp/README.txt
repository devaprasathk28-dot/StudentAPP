WEEK 4 - PROFESSIONAL HIBERNATE STUDENT MANAGEMENT SYSTEM
============================================================

This version keeps the Week 4 Hibernate requirement but adds a
professional interactive console interface.

FEATURES
--------
1. Add Student
2. Update Student
3. Search Student
4. View All Students
5. Delete Student
6. Input validation
7. Email validation
8. Duplicate ID protection
9. Transaction handling
10. MySQL database verification

TECH STACK
----------
Java 17
Maven
Hibernate ORM 6.6
Jakarta Persistence
MySQL
ByteXL Nimbus

PROJECT STRUCTURE
-----------------
src/main/java/com/example/
    App.java
    Student.java
    StudentDAO.java

src/main/resources/
    hibernate.cfg.xml

HOW TO IMPORT INTO ECLIPSE
---------------------------
1. Extract the ZIP.
2. Eclipse -> File -> Import.
3. Maven -> Existing Maven Projects.
4. Select the extracted project folder.
5. Finish.
6. Right-click project -> Maven -> Update Project.
7. Open App.java.
8. Right-click App.java -> Run As -> Java Application.

IMPORTANT
---------
The database credentials are included because this is a lab project.
Do not publish hibernate.cfg.xml or this ZIP publicly.

DATABASE VERIFICATION
---------------------
In ByteXL Nimbus run:

USE db_45566zh6z;

SELECT * FROM student;

The student records created through the console will appear there.

SAMPLE TEST
-----------
Choose:
1. Add Student

Enter:
Student ID: 101
Student Name: Deva Prasath
Email: deva@example.com
Course: B.Tech AI & DS

Then choose:
4. View All Students

Then:
2. Update Student

Student ID: 101
New Name: Deva Prasath
New Email: deva.updated@example.com
New Course: B.Tech Artificial Intelligence and Data Science

Then:
3. Search Student

Then verify in Nimbus:
SELECT * FROM student;

WHY THIS VERSION IS BETTER
---------------------------
The original lab requirement only needs insert/update using Hibernate.
This version additionally separates database operations into StudentDAO
and provides an interactive menu. It is still a console application,
so it remains simple and appropriate for a Hibernate lab.

NOTE
----
The application uses hibernate.hbm2ddl.auto=update. This is convenient
for a lab environment. Production applications should use controlled
database migrations instead.
