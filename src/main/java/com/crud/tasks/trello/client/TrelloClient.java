package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloAppToken;

    @Value("${trello.username}")
    private String username;

    private URI url(){
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/"+username+"/boards")
                    .queryParam("key", trelloAppKey)
                    .queryParam("token", trelloAppToken)
                    .queryParam("fields", "name, id").build().encode().toUri();
    }

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards(){


       Optional<TrelloBoardDto[]> boardsResponse = Optional.of(restTemplate.getForObject(url(), TrelloBoardDto[].class));

      return Arrays.asList(boardsResponse.get());

    }
}
