package com.taskboard.taskboard.controllers.api;

import com.taskboard.taskboard.entities.Board;
import com.taskboard.taskboard.entities.Category;
import com.taskboard.taskboard.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/add")
    public Board addBoard(@RequestBody Board board) {
        return boardService.createBoard(board);
    }

}