package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

import java.util.ArrayList;
import java.util.List;

//import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrelloController.class)
public class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrelloFacade facade;

    @Test
    public void shouldFetchEmptyTrelloBoards() throws Exception {
        //given
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        when(facade.fetchTrelloBoards()).thenReturn(trelloBoards);

        //when&then
        mockMvc.perform(get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void shouldFetchTrelloBoards() throws Exception{
        //given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "test", false));

        List<TrelloBoardDto> boards = new ArrayList<>();
        boards.add(new TrelloBoardDto("test", "1", trelloLists));

        when(facade.fetchTrelloBoards()).thenReturn(boards);

        //when&then
        mockMvc.perform(get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("test")))
                .andExpect(jsonPath("$[0].lists", hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].id", is("1")))
                .andExpect(jsonPath("$[0].lists[0].name", is("test")))
                .andExpect(jsonPath("$[0].lists[0].closed", is(false)));
    }

    @Test
    public void shouldCreateTrelloCard() throws Exception{
        //given
        TrelloCardDto card = new TrelloCardDto("test", "test", "bottom", "1");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("23", "test", "http://test.com");
        when(facade.createCard(ArgumentMatchers.any(TrelloCardDto.class))).thenReturn(createdCard);

        Gson gson = new Gson();
        String json = gson.toJson(card);

        //when&then
        mockMvc.perform(post("/v1/trello/createTrelloCard").contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(json))
                .andExpect(jsonPath("$.id", is("23")))
                .andExpect(jsonPath("$.name", is("test")))
                .andExpect(jsonPath("$.shortUrl", is("http://test.com")));

    }

}