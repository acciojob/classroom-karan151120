package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class StudentRepository {
    HashMap<String, Student> studentHashMap = new HashMap<>();
    HashMap<String, Teacher> teacherHashMap = new HashMap<>();
    HashMap<Teacher, List<String>> studentTeacherPairHashMap = new HashMap<>();


    public void addStudent(Student student) {
        String name = student.getName();

        studentHashMap.put(name, student);
    }

    public void addTeacher(Teacher teacher) {
        String name = teacher.getName();

        teacherHashMap.put(name, teacher);
    }


    public void addStudentTeacherPair(String student, String teacher) {
        Student student1 = studentHashMap.get(student);
        Teacher teacher1 = teacherHashMap.get(teacher);

        if(studentTeacherPairHashMap.containsKey(teacher1)) {
            List<String> l = studentTeacherPairHashMap.get(teacher1);
            l.add(student);
            studentTeacherPairHashMap.put(teacher1,l);
        }
        else {
            List<String> l = new ArrayList<>();
            l.add(student);
            studentTeacherPairHashMap.put(teacher1,l);
        }

    }

    public Student getStudentByName(String name) {
        return studentHashMap.get(name);
    }

    public Teacher getTeacherByName(String name) {
        return teacherHashMap.get(name);
    }

    public List<String> getStudentsByTeacherName(String teacher) {
        Teacher teacher1 = teacherHashMap.get(teacher);

        return studentTeacherPairHashMap.getOrDefault(teacher1, new ArrayList<>());
    }

    public List<String> getAllStudents() {
        List<String> l = new ArrayList<>();
        for(String s : studentHashMap.keySet()) {
            l.add(s);
        } return l;
    }

    public void deleteTeacherByName(String teacher) {
        Teacher teacher1 = teacherHashMap.get(teacher);
        List<String> l = studentTeacherPairHashMap.getOrDefault(teacher1, new ArrayList<>());

        studentTeacherPairHashMap.remove(teacher1);
        teacherHashMap.remove(teacher);

        for(String s : l) {
            studentHashMap.remove(s);
        }
    }

    public void deleteAllTeachers() {

        for(List<String> student : studentTeacherPairHashMap.values()) {
                for(String s : student) {
                    if(studentHashMap.containsKey(s)) studentHashMap.remove(s);
                }
        }

        for(Teacher teacher : studentTeacherPairHashMap.keySet()) {
            studentTeacherPairHashMap.remove(teacher);
        }

        for(String teacher : teacherHashMap.keySet()) {
            teacherHashMap.remove(teacher);
        }
    }
}
