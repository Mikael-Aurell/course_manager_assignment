package se.lexicon.course_manager_assignment.data.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.Collection;
import java.util.List;

@Service
public class StudentManager implements StudentService {

    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final Converters converters;

    @Autowired
    public StudentManager(StudentDao studentDao, CourseDao courseDao, Converters converters) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.converters = converters;
    }

    @Override
    public StudentView create(CreateStudentForm form) {

        if(form == null) {
            return null;
        }

        return converters.studentToStudentView(studentDao.createStudent(form.getName(), form.getEmail(), form.getAddress()));
    }

    @Override
    public StudentView update(UpdateStudentForm form) {

        if(form.getId() < 1) {
            return null;
        }

        Student studentToUpdate = studentDao.findById(form.getId());
        studentToUpdate.setName(form.getName());
        studentToUpdate.setEmail(form.getEmail());
        studentToUpdate.setAddress(form.getAddress());

        return converters.studentToStudentView(studentToUpdate);
    }

    @Override
    public StudentView findById(int id) {
        if(id < 1){
            return  null;
        }
        Student studentToFindById = studentDao.findById(id);

        return converters.studentToStudentView(studentToFindById);
    }

    @Override
    public StudentView searchByEmail(String email) {
        if(email == null){
            return  null;
        }

        Student studentToFindByEmail = studentDao.findByEmailIgnoreCase(email);

        return converters.studentToStudentView(studentToFindByEmail);
    }

    @Override
    public List<StudentView> searchByName(String name) {
        if(name == null){
            return  null;
        }

        Collection<Student> StudentToSearchByName = studentDao.findByNameContains(name);

        return converters.studentsToStudentViews(StudentToSearchByName);
    }


    @Override
    public boolean deleteStudent(int id) {
        if(id < 1){
            return false;
        }

        return studentDao.removeStudent(studentDao.findById(id));
    }

    @Override
    public List<StudentView> findAll() {
        Collection<Student> studentFindAll = studentDao.findAll();

        return converters.studentsToStudentViews(studentFindAll);
    }
}
