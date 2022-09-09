package com.spring_batch.batch;

import com.spring_batch.model.Student;
import com.spring_batch.repository.StudentRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<Student> {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void write(List<? extends Student> students) throws Exception {
        System.out.println("Data Saved for Students: "+students);
        studentRepository.saveAll(students);
    }
}
