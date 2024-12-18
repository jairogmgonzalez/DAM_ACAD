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

    @PostMapping("/create")
    public Board createBoard(Board board) {
        return boardService.createBoard(board);
    }

    @PutMapping("/update")
    public Board updateBoard(Board board) {
        return boardService.updateBoard(board);
    }

    @PatchMapping("/update-name/{id}")
    public void updateBoardName(@PathVariable("id") Long id, @RequestParam("newName") String newName) {
        boardService.updateBoardName(id, newName);
    }

    @PostMapping("/{boardId}/categories/add")
    public Board addCategory(@PathVariable Long boardId, @RequestBody Category category) {
        return boardService.addCategoryToBoard(boardId, category);
    }

    @DeleteMapping("/{boardId}/categories/remove")
    public Board removeCategory(@PathVariable Long boardId, @RequestBody Category category) {
        return boardService.removeCategoryFromBoard(boardId, category);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
    }

    @GetMapping("/{id}")
    public Board getBoard(@PathVariable("id") Long id) {
        return boardService.getBoardById(id);
    }

    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping("/by-name")
    public Board getBoardsByName(@RequestParam("name") String name) {
        return boardService.getBoardByName(name);
    }

    @GetMapping("/by-userid/{id}")
    public List<Board> getBoardsByUserid(@PathVariable("userId") Long userId) {
        return boardService.getBoardsByUserId(userId);
    }

    @GetMapping("/created-after")
    public List<Board> getCreatedAfter(@RequestParam("date") LocalDateTime date) {
        return boardService.getBoardsCreatedAfter(date);
    }

    @GetMapping("/count-with-desc")
    public Long getBoardsCountWithDesc(@RequestParam("date") LocalDateTime date) {
        return boardService.countBoardsWithDescription();
    }

    @GetMapping("/with-min-categories")
    public List<Board> getBoardsWithMinCategories(@RequestParam("minCategories") Long minCategories) {
        return boardService.getBoardsWithMinCategories(minCategories);
    }

    @GetMapping("/more-than-tasks")
    public List<Board> getMoreThanTasks(@RequestParam("minTasks") Long minTasks) {
        return boardService.getBoardsWithMoreThanXTasks(minTasks);
    }

    @GetMapping("/without-categories")
    public List<Board> getBoardsWithoutCategories(@RequestParam("minCategories") Long minCategories) {
        return boardService.getBoardsWithoutCategories();
    }

    @GetMapping("count-by-userid/{id}")
    public Long getBoardsCountByUserid(@PathVariable("id") Long userId) {
        return boardService.countBoardsByUserId(userId);
    }




}
