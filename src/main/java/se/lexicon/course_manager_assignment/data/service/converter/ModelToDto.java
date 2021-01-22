package se.lexicon.course_manager_assignment.data.service.converter;

import org.springframework.stereotype.Component;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ModelToDto implements Converters {

    @Override
    public StudentView studentToStudentView(Student student) {
        if(student.equals(null)){
            throw new IllegalArgumentException("The object Student is empty.");
        }
        StudentView studentView = new StudentView(student.getId(), student.getName(),
                student.getEmail(), student.getAddress());
        return studentView;
    }

    @Override
    public CourseView courseToCourseView(Course course) {
        if(course.equals(null)){
            throw new IllegalArgumentException("The object Student is empty.");
        }
        CourseView courseView = new CourseView(
                course.getId(),
                course.getCourseName(),
                course.getStartDate(),
                course.getWeekDuration(),
                studentsToStudentViews(course.getStudents()));
        return courseView;
    }

    @Override
    public List<CourseView> coursesToCourseViews(Collection<Course> courses) {
        if(courses.equals(null)){
            throw new IllegalArgumentException("The object Courses are empty.");
        }
        List<CourseView> courseViewList = new ArrayList<>();
        for (Course course : courses){
             courseViewList.add(courseToCourseView(course));
        }
        return courseViewList;
    }

    @Override
    public List<StudentView> studentsToStudentViews(Collection<Student> students) {
        if(students.equals(null)){
            throw new IllegalArgumentException("The object Students are empty.");
        }
        List<StudentView> studentViewList = new ArrayList<>();
        for (Student student : students){
            studentViewList.add(studentToStudentView(student));
        }
        return studentViewList;
    }
}
