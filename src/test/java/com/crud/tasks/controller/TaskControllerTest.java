package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskMapper mapper;
    @MockBean
    private DbService service;

    @Test
    public void shouldGetEmptyList() throws Exception{
        //given
        List<Task> tasks = new ArrayList<>();
        List<TaskDto> taskDtos = new ArrayList<>();
   //     when(service.getAllTasks()).thenReturn(tasks);
        when(mapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);

        //when&then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void shouldGetList() throws Exception{
        //given
        List<Task> tasks = new ArrayList<>();
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(new TaskDto(1L, "test", "test"));
        when(mapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);

        //when&then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test")))
                .andExpect(jsonPath("$[0].content", is("test")));
    }

    @Test
    public void shouldReturnOneTask() throws Exception{
        Task result = new Task(2L, "result", "result");
        TaskDto dtoResult = new TaskDto(2L, "dtoResult", "result");

        when(service.getTask(2L)).thenReturn(Optional.of(result));
        when(mapper.mapToTaskDto(result)).thenReturn(dtoResult);

        //when & then
        mockMvc.perform(get("/v1/task/getTask?id=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("dtoResult")))
                .andExpect(jsonPath("$.content", is("result")));
    }

    @Test
    public void shouldDeleteTask() throws Exception{
        //when&then
        mockMvc.perform(delete("/v1/task/deleteTask?id=2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateTask() throws Exception{
        //given
        Task task = new Task(1L, "test", "test");
        TaskDto taskDto = new TaskDto(1L, "test", "test");
        when(mapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String json = gson.toJson(taskDto);

        //when&then
        mockMvc.perform(post("/v1/task/createTask").contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTask() throws Exception{
        Task task = new Task(1L, "test", "test");
        TaskDto updatedTask = new TaskDto(1L, "test2", "updated");

        when(mapper.mapToTask(updatedTask)).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);
        when(mapper.mapToTaskDto(task)).thenReturn(updatedTask);

        Gson gson = new Gson();
        String json = gson.toJson(updatedTask);

        //when&then
        mockMvc.perform(put("/v1/task/updateTask").contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(json))
                .andExpect(status().isOk());
    }

}