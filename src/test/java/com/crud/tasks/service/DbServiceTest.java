package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DbServiceTest {

    @InjectMocks
    DbService service;

    @Mock
    TaskRepository repository;

    @Test
    public void getAllTasks() {
        //given
        Task task1 = new Task(1L, "1", "1");
        Task task2 = new Task(2L, "2", "2");
        Task task3 = new Task(3L, "3", "3");

        List<Task> list = new ArrayList<>();
        list.add(task1);
        list.add(task2);
        list.add(task3);

        when(repository.findAll()).thenReturn(list);

        //when
        List<Task> result = service.getAllTasks();

        //then
        Assert.assertEquals(3, result.size());
    }

    @Test
    public void getTask() {
        //given
        Task task1 = new Task(1L, "1", "1task");

        when(repository.findById(1L)).thenReturn(Optional.of(task1));

        //when
        Optional<Task> result = service.getTask(1L);

        //then
        Assert.assertEquals(1, result.get().getId(), 0);
        Assert.assertEquals("1", result.get().getTitle());
        Assert.assertEquals("1task", result.get().getContent());

    }

    @Test
    public void saveTask() {
        //given
        Task task1 = new Task(1L, "1", "1task");

        when(repository.save(task1)).thenReturn(task1);

        //when
        Task result = service.saveTask(task1);

        //then
        Assert.assertEquals(result, task1);
    }

    @Test
    public void deleteTaskByIdTest(){
        //given
        Long taskId = 1L;

        //when
        service.deleteById(taskId);

        //then
        verify(repository, times(1)).deleteById(eq(taskId));
    }
}