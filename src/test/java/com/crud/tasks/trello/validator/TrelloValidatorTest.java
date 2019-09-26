package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
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
public class TrelloValidatorTest {

    @Autowired
    TrelloValidator validator;

    @Test
    public void validateTestCard() {
        //given
        TrelloCard card = new TrelloCard("test", "test", "bottom", "1");
        //when
        validator.validateCard(card);
        //then
        //do nothing
    }

    @Test
    public void validateBoardListTest(){
        //given
        List<TrelloBoard> boards = new ArrayList<>();
        boards.add(new TrelloBoard("1", "testing", new ArrayList<>()));
        boards.add(new TrelloBoard("2", "test", new ArrayList<>()));
        boards.add(new TrelloBoard("3", "protest", new ArrayList<>()));

        //when
        List<TrelloBoard> result = validator.validateTrelloBoards(boards);

        //then
        Assert.assertEquals(2, result.size());
    }
}