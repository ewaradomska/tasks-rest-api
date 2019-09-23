package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void mapToBoardsList() {
        //given
        List<TrelloBoardDto> dtoList = new ArrayList<>();
        TrelloBoardDto first = new TrelloBoardDto("first", "1", new ArrayList<>());
        TrelloBoardDto second = new TrelloBoardDto("second", "2", new ArrayList<>());
        dtoList.add(first);
        dtoList.add(second);

        //when
        List<TrelloBoard> boardsList = trelloMapper.mapToBoardsList(dtoList);
        String name = boardsList.get(0).getName();
        String name2 = boardsList.get(1).getName();

        //then
        Assert.assertEquals("first", name);
        Assert.assertEquals("second", name2);
    }

    @Test
    public void mapToBoardDtoList() {
        //given
        List<TrelloBoard> boardsList = new ArrayList<>();
        TrelloBoard first = new TrelloBoard("1", "first", new ArrayList<>());
        TrelloBoard second = new TrelloBoard("2", "second", new ArrayList<>());
        boardsList.add(first);
        boardsList.add(second);

        //when
        List<TrelloBoardDto> dtoList = trelloMapper.mapToBoardDtoList(boardsList);
        String name = dtoList.get(0).getName();
        String name2 = dtoList.get(1).getName();

        //then
        Assert.assertEquals("first", name);
        Assert.assertEquals("second", name2);
    }

    @Test
    public void mapToList() {
        //given
        List<TrelloListDto> dtoList = new ArrayList<>();
        TrelloListDto first = new TrelloListDto("1", "first", true);
        TrelloListDto second = new TrelloListDto("2", "second", false);
        dtoList.add(first);
        dtoList.add(second);

        //when
        List<TrelloList> listList = trelloMapper.mapToList(dtoList);
        String name = listList.get(0).getName();
        String name2 = listList.get(1).getName();

        //then
        Assert.assertEquals("first", name);
        Assert.assertEquals("second", name2);
    }

    @Test
    public void mapToListDto() {
        //given
        List<TrelloList> listList = new ArrayList<>();
        TrelloList first = new TrelloList("1", "first", true);
        TrelloList second = new TrelloList("2", "second", false);
        listList.add(first);
        listList.add(second);

        //when
        List<TrelloListDto> dtoList = trelloMapper.mapToListDto(listList);
        String name = dtoList.get(0).getName();
        String name2 = dtoList.get(1).getName();

        //then
        Assert.assertEquals("first", name);
        Assert.assertEquals("second", name2);
    }

    @Test
    public void mapToCard() {
        //given
        TrelloCardDto cardDto = new TrelloCardDto("card", "test", "bottom", "1");

        //when
        TrelloCard card = trelloMapper.mapToCard(cardDto);
        String name = card.getName();
        String description = card.getDescription();
        String position = card.getPosition();
        String listId = card.getListId();

        //then
        Assert.assertEquals("card", name);
        Assert.assertEquals("test", description);
        Assert.assertEquals("bottom", position);
        Assert.assertEquals("1", listId);
    }

    @Test
    public void mapToCardDto() {
        //given
        TrelloCard card = new TrelloCard("card", "test", "bottom", "1");

        //when
        TrelloCardDto cardDto = trelloMapper.mapToCardDto(card);
        String name = cardDto.getName();
        String description = cardDto.getDescription();
        String position = cardDto.getPosition();
        String listId = cardDto.getListId();

        //then
        Assert.assertEquals("card", name);
        Assert.assertEquals("test", description);
        Assert.assertEquals("bottom", position);
        Assert.assertEquals("1", listId);
    }
}