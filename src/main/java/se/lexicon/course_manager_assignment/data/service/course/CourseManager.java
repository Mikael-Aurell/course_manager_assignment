package se.lexicon.course_manager_assignment.data.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class CourseManager implements CourseService {

    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final Converters converters;

    @Autowired
    public CourseManager(CourseDao courseDao, StudentDao studentDao, Converters converters) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.converters = converters;
    }

    @Override
    public CourseView create(CreateCourseForm form) {
        if(form == null) {
            return null;
        }
        return converters.courseToCourseView(courseDao.createCourse(form.getCourseName(), form.getStartDate(), form.getWeekDuration()));
    }

    @Override
    public CourseView update(UpdateCourseForm form) {
        if (form.getId() < 1){
            return null;
        }
        Course courseToUpdate = courseDao.findById(form.getId());
        courseToUpdate.setCourseName(form.getCourseName());
        courseToUpdate.setStartDate(form.getStartDate());
        courseToUpdate.setWeekDuration(form.getWeekDuration());

        return converters.courseToCourseView(courseToUpdate);
    }

    @Override
    public List<CourseView> searchByCourseName(String courseName) {
        if(courseName == null) {
            return null;
        }

        Collection<Course> courseToSearchByName = courseDao.findByNameContains(courseName);

        return converters.coursesToCourseViews(courseToSearchByName);
    }

    @Override
    public List<CourseView> searchByDateBefore(LocalDate end) {
        if(end == null) {
            return null;
        }

        Collection <Course> coursesToSearchByDateBefore = courseDao.findByDateBefore(end);

        return converters.coursesToCourseViews(coursesToSearchByDateBefore);
    }

    @Override
    public List<CourseView> searchByDateAfter(LocalDate start) {
        if(start == null) {
            return null;
        }

        Collection <Course> coursesToSearchByDateAfter = courseDao.findByDateAfter(start);

        return converters.coursesToCourseViews(coursesToSearchByDateAfter);
    }

    @Override
    public boolean addStudentToCourse(int courseId, int studentId) {
        if(courseId < 1 && studentId < 1 || courseId < 1 || studentId < 1) {
            return false;
        }

        Course courseToAddStudentTo = courseDao.findById(courseId);
        boolean addStudentToCourseBoolean = courseToAddStudentTo.enrollStudents(studentDao.findById(studentId));

        return addStudentToCourseBoolean;
    }

    @Override
    public boolean removeStudentFromCourse(int courseId, int studentId) {
        if(courseId < 1 && studentId < 1 || courseId < 1 || studentId < 1) {
            return false;
        }

        boolean removeStudentFromCourseBoolean = courseDao.findById(courseId).unEnrollStudents(studentDao.findById(studentId));

        return removeStudentFromCourseBoolean;
    }

    @Override
    public CourseView findById(int id) {
        if(id < 1){
            return null;
        }

        Course courseToFindById = courseDao.findById(id);

        return converters.courseToCourseView(courseToFindById);
    }

    @Override
    public List<CourseView> findByStudentId(int studentId) {
        if(studentId < 1){
            return null;
        }
        Collection <Course> courseToFindByStudentId = courseDao.findByStudentId(studentId);

        return converters.coursesToCourseViews(courseToFindByStudentId);
    }

    @Override
    public boolean deleteCourse(int id) {
        if(id < 1){
            return false;
        }

        boolean courseToDeleteBoolean = courseDao.removeCourse(courseDao.findById(id));

        return courseToDeleteBoolean;
    }

    @Override
    public List<CourseView> findAll() {
        Collection <Course> courseToFindAll = courseDao.findAll();

        return converters.coursesToCourseViews(courseToFindAll);
    }
}
