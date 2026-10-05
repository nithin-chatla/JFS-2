package com.campusconnect.service;

import com.campusconnect.model.Course;
import com.campusconnect.model.Department;
import com.campusconnect.repository.CourseRepository;
import com.campusconnect.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollegeService {

    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;

    public CollegeService(DepartmentRepository departmentRepository, CourseRepository courseRepository) {
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
    }

    // Departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department updated) {
        return departmentRepository.findById(id).map(dept -> {
            dept.setName(updated.getName());
            dept.setCode(updated.getCode());
            dept.setDescription(updated.getDescription());
            dept.setHeadOfDepartment(updated.getHeadOfDepartment());
            dept.setBuilding(updated.getBuilding());
            dept.setContactEmail(updated.getContactEmail());
            dept.setStudentCount(updated.getStudentCount());
            dept.setFacultyCount(updated.getFacultyCount());
            return departmentRepository.save(dept);
        }).orElseThrow(() -> new RuntimeException("Department not found with id " + id));
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    // Courses
    public List<Course> getAllCourses(String department) {
        if (department != null && !department.trim().equalsIgnoreCase("ALL")) {
            return courseRepository.findByDepartment(department.trim());
        }
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updated) {
        return courseRepository.findById(id).map(course -> {
            course.setCode(updated.getCode());
            course.setTitle(updated.getTitle());
            course.setDepartment(updated.getDepartment());
            course.setCredits(updated.getCredits());
            course.setSemester(updated.getSemester());
            course.setInstructorName(updated.getInstructorName());
            course.setDescription(updated.getDescription());
            return courseRepository.save(course);
        }).orElseThrow(() -> new RuntimeException("Course not found with id " + id));
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
