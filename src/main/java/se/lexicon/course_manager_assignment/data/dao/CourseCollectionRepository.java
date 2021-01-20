package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;

import java.time.LocalDate;
import java.util.*;


public class CourseCollectionRepository implements CourseDao{

    private Collection<Course> courses;


    public CourseCollectionRepository(Collection<Course> courses) {
        this.courses = courses;
    }

    @Override
    public Course createCourse(String courseName, LocalDate startDate, int weekDuration) {
            int id = CourseSequencer.nextCourseId();
            Course newCourse = new Course(id, courseName, startDate, weekDuration);
            if (newCourse == null){
                throw new IllegalArgumentException("object is null");
            }
            if ( courseName == null){
                throw new IllegalArgumentException("object is null");
            }
            Course checkCourseDuplicate = findById(newCourse.getId());
            if (checkCourseDuplicate != null){
                throw new IllegalArgumentException("Student exists");

            }
            courses.add(newCourse);
        return newCourse;
        }


    @Override
    public Course findById(int id) {
        if (id < 1 ) {
            throw new IllegalArgumentException("id is not valid");
        }
        Course courseToFindById = null;
        for (Course student : courses) {
            // if condition
            if (id == student.getId()) {
                courseToFindById = student;
                break;
            }
        }
        return courseToFindById;
    }

    @Override
    public Collection<Course> findByNameContains(String name) {
        List<Course> result = new ArrayList<>();
        for (Course course : courses) {
            for (Student student : course.getStudents()) {
                if (student.equals(name.toLowerCase())) {
                    result.add(course);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<Course> findByDateBefore(LocalDate end) {
        return null;
    }

    @Override
    public Collection<Course> findByDateAfter(LocalDate start) {
        return null;
    }

    @Override
    public Collection<Course> findAll() {
        return null;
    }

    @Override
    public Collection<Course> findByStudentId(int studentId) {
        return null;
    }

    @Override
    public boolean removeCourse(Course course) {
        return false;
    }

    @Override
    public void clear() {
        this.courses = new HashSet<>();
    }
}
