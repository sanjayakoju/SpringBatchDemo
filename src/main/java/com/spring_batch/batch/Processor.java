package com.spring_batch.batch;

import com.spring_batch.model.Student;
import com.spring_batch.model.StudentCSV;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Component
public class Processor implements ItemProcessor<StudentCSV, Student> {

    public Processor() {
    }

    @Override
    public Student process(StudentCSV studentCSV) throws Exception {
        Student student = new Student();
        student.setFirst(studentCSV.getFirst());
        student.setLast(studentCSV.getLast());
        student.setGpa(studentCSV.getGPA());
        LocalDate dob = LocalDate.now().minusYears(studentCSV.getAGE()).with(TemporalAdjusters.firstDayOfYear());
        student.setDob(dob);
        return student;
    }
}
