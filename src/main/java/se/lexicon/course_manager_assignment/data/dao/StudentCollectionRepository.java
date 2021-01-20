package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Student;

import java.util.*;


public class StudentCollectionRepository implements StudentDao {

    private Collection<Student> students;

    public StudentCollectionRepository(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public Student createStudent(String name, String email, String address) {
        int id = StudentSequencer.nextStudentId();
        Student newStudent = new Student(id, name, email, address);
        if (newStudent == null){
            throw new IllegalArgumentException("object is null");
        }
        if ( email == null){
            throw new IllegalArgumentException("object is null");
        }
        Student checkStudentDuplicate = findById(newStudent.getId());
        if (checkStudentDuplicate != null){
            throw new IllegalArgumentException("Student exists");

        }
        students.add(newStudent);
        return newStudent;
    }

    @Override
    public Student findByEmailIgnoreCase(String email) {
        if (email == null) {
            throw new IllegalArgumentException("email is not valid");
        }
        Student studentToFindByEmail = null;
        for (Student student : students) {
            // if condition
            if (email.equalsIgnoreCase(student.getEmail().toLowerCase())) {
                studentToFindByEmail = student;
                break;
            }
        }
        return studentToFindByEmail;
    }

    @Override
    public Collection<Student> findByNameContains(String name) {
        List<Student> findByNameContains = new ArrayList<>();
        for (Student student : students) {
            if (name.equalsIgnoreCase(student.getName())) {
                findByNameContains.add(student);
            }
        }
        return findByNameContains;
    }

    @Override
    public Student findById(int id) {
        if (id < 1 ) {
            throw new IllegalArgumentException("id is not valid");
        }
        Student studentToFindById = null;
        for (Student student : students) {
            // if condition
            if (id == student.getId()) {
                studentToFindById = student;
                break;
            }
        }
        return studentToFindById;
    }

    @Override
    public Collection<Student> findAll() {
        return new ArrayList<>(students);
    }

    @Override
    public boolean removeStudent(Student student) {
            boolean isDelete = false;
            Iterator<Student> iterator = students.iterator();
            while (iterator.hasNext()) {
                Student result = iterator.next();
                if (result.equals(student)) {
                    iterator.remove();
                    isDelete = true;
                }
            }
            return isDelete;
    }

    @Override
    public void clear() {
        this.students = new HashSet<>();
    }
}
