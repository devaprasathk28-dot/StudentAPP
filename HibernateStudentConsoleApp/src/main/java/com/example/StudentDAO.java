package com.example;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class StudentDAO {

    private final SessionFactory sessionFactory;

    public StudentDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean addStudent(Student student) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            if (session.get(Student.class, student.getId()) != null) {
                transaction.rollback();
                return false;
            }

            session.persist(student);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error while adding student: " + e.getMessage());
            return false;
        }
    }

    public boolean updateStudent(Student student) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Student existing = session.get(Student.class, student.getId());

            if (existing == null) {
                transaction.rollback();
                return false;
            }

            existing.setName(student.getName());
            existing.setEmail(student.getEmail());
            existing.setCourse(student.getCourse());

            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error while updating student: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteStudent(int id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Student student = session.get(Student.class, id);

            if (student == null) {
                transaction.rollback();
                return false;
            }

            session.remove(student);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error while deleting student: " + e.getMessage());
            return false;
        }
    }

    public Student getStudent(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Student.class, id);
        } catch (Exception e) {
            System.out.println("Error while searching student: " + e.getMessage());
            return null;
        }
    }

    public List<Student> getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM Student ORDER BY id", Student.class)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error while loading students: " + e.getMessage());
            return List.of();
        }
    }
}
