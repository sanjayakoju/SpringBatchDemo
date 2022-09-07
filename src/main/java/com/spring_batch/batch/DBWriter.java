package com.spring_batch.batch;

import com.spring_batch.model.User;
import com.spring_batch.repository.UserRespsitory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<User> {

    @Autowired
    private UserRespsitory userRespsitory;

    @Override
    public void write(List<? extends User> users) throws Exception {
        System.out.println("Data Saved for Users: "+users);
        userRespsitory.saveAll(users);
    }
}
