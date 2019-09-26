package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTest {

    @Autowired
    TaskMapper mapper;

    @Test
    public void mapToTask() {
        //given
        TaskDto taskDto = new TaskDto(1L, "title", "content");

        //when
        Task task = mapper.mapToTask(taskDto);

        //then
        Assert.assertEquals(taskDto.getId(), task.getId());
        Assert.assertEquals("title", task.getTitle());
        Assert.assertEquals("content", task.getContent());

    }

    @Test
    public void mapToTaskDto() {
        //given
        Task task = new Task(1L, "title", "content");

        //when
        TaskDto taskDto = mapper.mapToTaskDto(task);

        //then
        Assert.assertEquals(task.getId(), taskDto.getId());
        Assert.assertEquals("title", taskDto.getTitle());
        Assert.assertEquals("content", taskDto.getContent());
    }

    @Test
    public void mapToTaskDtoList() {
        //given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "title", "content"));

        //when
        List<TaskDto> taskDtos = mapper.mapToTaskDtoList(taskList);

        //then
        taskDtos.forEach(taskDto -> {
            assertEquals("title", taskDto.getTitle());
            assertEquals("content", taskDto.getContent());
        });
    }
}