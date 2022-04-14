package com.example.memoserver.controller;


import com.example.memoserver.entity.Board;
import com.example.memoserver.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/board")
public class BoardController
{
    @Autowired
    private BoardService boardService;


    @GetMapping("")
    public String getAllBoard()
    {
        boardService.getAllBoard();

        JSONArray jsonArray = new JSONArray();

        List<Board> boardList = boardService.getAllBoard();
        for(Board board:boardList)
        {
            JSONObject obj = new JSONObject();
            obj.put("board_idx",board.getBoard_idx());
            obj.put("board_name",board.getBoard_name());

            jsonArray.put(obj);
        }
        return jsonArray.toString();
    }

    @GetMapping("/getBoard")
    public void getBoard(Long content_board_idx)
    {

    }
}