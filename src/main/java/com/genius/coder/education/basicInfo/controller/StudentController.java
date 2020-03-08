package com.genius.coder.education.basicInfo.controller;

import com.genius.coder.base.controller.VueController;
import com.genius.coder.base.form.BaseForm;
import com.genius.coder.education.basicInfo.domain.Student;
import com.genius.coder.education.basicInfo.form.StudentForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/8
 */
@Controller
@RequestMapping("/student")
public class StudentController extends VueController<Student,String> {
    @Override
    protected BaseForm<Student, String> getForm(Student student) {
        return new StudentForm().toForm(student);
    }
}
