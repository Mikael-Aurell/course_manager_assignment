package se.lexicon.course_manager_assignment.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {StudentCollectionRepository.class})
public class StudentCollectionRepositoryTest {

    @Autowired
    private StudentDao testObject;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(testObject == null);
    }

    @Test
    public void test_Create_Student () {

        Student student1 = new Student(1,"Bosse", "bosse@gmail.com", "Ekvägen 3");
        Student student2 = new Student(2,"Lasse", "lasse@gmail.com", "Ekvägen 4");
        Student student3 = new Student(3,"Jan", "jan@gmail.com", "Ekvägen 5");

        assertEquals(student1, testObject.createStudent(student1.getName(), student1.getEmail(), student1.getAddress()));
        testObject.createStudent(student2.getName(), student2.getEmail(), student2.getAddress());
        assertEquals(student3, testObject.createStudent(student3.getName(), student3.getEmail(), student3.getAddress()));

    }

    @Test
    public void test_findByEmailIgnoreCase(){ //return student
        Student student1 = new Student(1,"Bosse", "bosse@gmail.com", "Ekvägen 3");

        testObject.createStudent(student1.getName(), student1.getEmail(), student1.getAddress());
        assertEquals(student1, testObject.findByEmailIgnoreCase(student1.getEmail()));
    }

    @Test
    public void test_findByNameContains(){
        List<Student> expectedStudents = new ArrayList<>();
        Student student1 = new Student(1,"Bosse", "bosse@gmail.com", "Ekvägen 3");
        Student student2 = new Student(2,"Lasse", "lasse@gmail.com", "Ekvägen 4");
        Student student3 = new Student(3,"Jan", "jan@gmail.com", "Ekvägen 5");
        Student student4 = new Student(4,"Bosse", "bosse2@gmail.com", "Örnvägen 3");
        expectedStudents.add(student1);
        expectedStudents.add(student4);

        testObject.createStudent(student1.getName(), student1.getEmail(), student1.getAddress());
        testObject.createStudent(student2.getName(), student2.getEmail(), student2.getAddress());
        testObject.createStudent(student3.getName(), student3.getEmail(), student3.getAddress());
        testObject.createStudent(student4.getName(), student4.getEmail(), student4.getAddress());
        Collection<Student> actualStudents = testObject.findByNameContains("Bosse");

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    public void test_findById() {
        Student student1 = new Student(1,"Bosse", "bosse@gmail.com", "Ekvägen 3");

        testObject.createStudent(student1.getName(), student1.getEmail(), student1.getAddress());
        assertEquals(student1, testObject.findById(student1.getId()));
    }

    @Test
    public void test_findAll(){
        List<Student> expectedAllStudents = new ArrayList<>();
        Student student1 = new Student(1,"Bosse", "bosse@gmail.com", "Ekvägen 3");
        Student student2 = new Student(2,"Lasse", "lasse@gmail.com", "Ekvägen 4");
        Student student3 = new Student(3,"Jan", "jan@gmail.com", "Ekvägen 5");

        expectedAllStudents.add(student1);
        expectedAllStudents.add(student2);
        expectedAllStudents.add(student3);

        testObject.createStudent(student1.getName(), student1.getEmail(), student1.getAddress());
        testObject.createStudent(student2.getName(), student2.getEmail(), student2.getAddress());
        testObject.createStudent(student3.getName(), student3.getEmail(), student3.getAddress());

        Collection<Student> actualAllStudents = testObject.findAll();

        assertEquals(expectedAllStudents,actualAllStudents);
    }

    @Test
    public void test_removeStudent(){

        Student student1 = new Student(1,"Bosse", "bosse@gmail.com", "Ekvägen 3");
        Student student2 = new Student(2,"Lasse", "lasse@gmail.com", "Ekvägen 4");
        Student student3 = new Student(3,"Jan", "jan@gmail.com", "Ekvägen 5");


        testObject.createStudent(student1.getName(), student1.getEmail(), student1.getAddress());
        testObject.createStudent(student2.getName(), student2.getEmail(), student2.getAddress());
        testObject.createStudent(student3.getName(), student3.getEmail(), student3.getAddress());

        boolean actual = testObject.removeStudent(student2);

        //assertEquals(true,actual);
        assertEquals(student3,testObject.findAll().toArray()[1]);
    }

    @Test
    public void test_clear(){
        Student student1 = new Student(1,"Bosse", "bosse@gmail.com", "Ekvägen 3");
        Student student2 = new Student(2,"Lasse", "lasse@gmail.com", "Ekvägen 4");
        Student student3 = new Student(3,"Jan", "jan@gmail.com", "Ekvägen 5");


        testObject.createStudent(student1.getName(), student1.getEmail(), student1.getAddress());
        testObject.createStudent(student2.getName(), student2.getEmail(), student2.getAddress());
        testObject.createStudent(student3.getName(), student3.getEmail(), student3.getAddress());

        testObject.clear();

        assertNotEquals(student1, testObject.findById(1));

    }

    @AfterEach
    void tearDown(){
        testObject.clear();
        StudentSequencer.setStudentSequencer(0);
    }
}
