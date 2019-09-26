package com.crud.tasks.service;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TrelloServiceTest {

    @InjectMocks
    TrelloService service;

    @Mock
    TrelloClient client;


    @Test
    public void returnListIfThereIsNoBoards() {
        //given
        when(client.getTrelloBoards()).thenReturn(new ArrayList<>());

        //when
        List<TrelloBoardDto> list = service.fetchTrelloBoards();

        //then
        Assert.assertEquals(0, list.size());
    }
}