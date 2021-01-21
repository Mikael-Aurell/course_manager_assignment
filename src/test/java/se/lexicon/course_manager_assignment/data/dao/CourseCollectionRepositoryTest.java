package se.lexicon.course_manager_assignment.data.dao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = {CourseCollectionRepository.class})
public class CourseCollectionRepositoryTest {

    @Autowired
    private CourseDao testObject;
    private StudentDao testObjectStudent;

    Student student1 = new Student(1,"Bosse", "bosse@gmail.com", "Ekvägen 3");
    Student student2 = new Student(2,"Lasse", "lasse@gmail.com", "Ekvägen 4");
    Student student3 = new Student(3,"Jan", "jan@gmail.com", "Ekvägen 5");
    Student student4 = new Student(4,"Bosse", "bosse2@gmail.com", "Örnvägen 3");

    LocalDate course1Date = LocalDate.of(2020, 1, 1);
    LocalDate course2Date = LocalDate.of(2020, 1, 15);
    LocalDate course3Date = LocalDate.of(2020, 2, 1);


    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(testObject == null);
    }

    @Test
    public void test_createCourse() {
        Course course1 = new Course(1, "Bootstrap", course1Date, 8);
        Course course2 = new Course(2, "Java", course2Date, 12);
        Course course3 = new Course(3, "C#", course3Date, 8);

        Course expectedCourse = course1;
        Course actualCourse = testObject.createCourse(course1.getCourseName(),course1.getStartDate(),course1.getWeekDuration());

    assertEquals(expectedCourse, actualCourse);

    }

    @Test
    public void test_findById(){
        Course course1 = new Course(1, "Bootstrap", course1Date, 8);
        Course course2 = new Course(2, "Java", course2Date, 12);
        Course course3 = new Course(3, "C#", course3Date, 8);

        testObject.createCourse(course1.getCourseName(),course1.getStartDate(),course1.getWeekDuration());
        testObject.createCourse(course2.getCourseName(),course2.getStartDate(),course2.getWeekDuration());
        testObject.createCourse(course3.getCourseName(),course3.getStartDate(),course3.getWeekDuration());

        Course expectedCourse = course2;
        Course actualCourse = testObject.findById(2);

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    public void test_findByNameContains(){
        List<Student> studentList1 = new ArrayList<>();
        studentList1.add(student1);
        studentList1.add(student4);

        List<Student> studentList2 = new ArrayList<>();
        studentList1.add(student1);
        studentList1.add(student2);
        studentList1.add(student3);
        studentList1.add(student4);

        List<Student> studentList3 = new ArrayList<>();
        studentList1.add(student1);

        Course course1 = new Course(1, "Bootstrap", course1Date, 8, studentList1);
        Course course2 = new Course(2, "Java", course2Date, 12, studentList2);
        Course course3 = new Course(3, "C#", course3Date, 8, studentList3);

        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(course1);
        expectedCourses.add(course2);
        expectedCourses.add(course3);

        testObject.createCourse(course1.getCourseName(),course1.getStartDate(),course1.getWeekDuration());
        testObject.createCourse(course2.getCourseName(),course2.getStartDate(),course2.getWeekDuration());
        testObject.createCourse(course3.getCourseName(),course3.getStartDate(),course3.getWeekDuration());

        assertEquals(student4,testObject.findById(1).getStudents().toArray()[1]);
    }

    @Test
    public void test_findByDateBefore(){}

    @Test
    public void test_findByDateAfter(){}

    @Test
    public void test_findAll(){}

    @Test
    public void test_findByStudentId(){}

    @Test
    public void test_removeCourse(){}

    @Test
    public void test_clearAll(){}


    @AfterEach
    void tearDown() {
        testObject.clear();
        CourseSequencer.setCourseSequencer(0);
    }
}
