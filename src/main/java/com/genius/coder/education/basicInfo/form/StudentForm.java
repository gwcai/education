package com.genius.coder.education.basicInfo.form;

import com.genius.coder.base.form.BaseForm;
import com.genius.coder.education.basicInfo.domain.Student;
import lombok.Data;

import java.util.Date;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/8
 */
@Data
public class StudentForm extends BaseForm<Student,String> {
    private String no;
    private String name;
    private String idNo;
    private String gander;
    private Date birthDate;
    private String father;
    private String mother;
    private String f_phone;
    private String m_phone;
    private String imgCode;
}
