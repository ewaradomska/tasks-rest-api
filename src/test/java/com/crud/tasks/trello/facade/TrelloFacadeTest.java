package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TrelloFacadeTest {
    @InjectMocks
    private TrelloFacade facade;

    @Mock
    private TrelloService service;

    @Mock
    private TrelloMapper mapper;

    @Mock
    private TrelloValidator validator;

    @Test
    public void shouldFetchEmptyList(){
        //given
        List<TrelloListDto> dtoList = new ArrayList<>();
        dtoList.add(new TrelloListDto("1", "test", false));

        List<TrelloBoardDto> dtoBoardList = new ArrayList<>();
        dtoBoardList.add(new TrelloBoardDto("test", "1", dtoList));

        List<TrelloList> mappedList = new ArrayList<>();
        mappedList.add(new TrelloList("1", "test", false));

        List<TrelloBoard> mappedBoardList = new ArrayList<>();
        mappedBoardList.add(new TrelloBoard("1", "test", mappedList));

        when(service.fetchTrelloBoards()).thenReturn(dtoBoardList);
        when(mapper.mapToBoardsList(dtoBoardList)).thenReturn(mappedBoardList);
        when(mapper.mapToBoardDtoList(anyList())).thenReturn(new ArrayList<>());
        when(validator.validateTrelloBoards(mappedBoardList)).thenReturn(new ArrayList<>());

        //when
        List<TrelloBoardDto> boards = facade.fetchTrelloBoards();

        //then
        Assert.assertNotNull(boards);
        Assert.assertEquals(0, boards.size());
    }

    @Test
    public void shouldfetchTrelloBoards() {
        //given
        List<TrelloListDto> dtoList = new ArrayList<>();
        dtoList.add(new TrelloListDto("1", "first", false));

        List<TrelloBoardDto> dtoBoardList = new ArrayList<>();
        dtoBoardList.add(new TrelloBoardDto("first", "1", dtoList));

        List<TrelloList> mappedList = new ArrayList<>();
        mappedList.add(new TrelloList("1", "first", false));

        List<TrelloBoard> mappedBoardList = new ArrayList<>();
        mappedBoardList.add(new TrelloBoard("1", "first", mappedList));

        when(service.fetchTrelloBoards()).thenReturn(dtoBoardList);
        when(mapper.mapToBoardsList(dtoBoardList)).thenReturn(mappedBoardList);
        when(mapper.mapToBoardDtoList(anyList())).thenReturn(dtoBoardList);
        when(validator.validateTrelloBoards(mappedBoardList)).thenReturn(mappedBoardList);

        //when
        List<TrelloBoardDto> boards = facade.fetchTrelloBoards();

        //then
        Assert.assertNotNull(boards);
        Assert.assertEquals(1, boards.size());

        boards.forEach(board -> {
            assertEquals("1", board.getId());
            assertEquals("first", board.getName());

            board.getLists().forEach(list ->{
                assertEquals("1", list.getId());
                assertEquals("first", list.getName());
                assertFalse(list.isClosed());
            });
        });
    }

}