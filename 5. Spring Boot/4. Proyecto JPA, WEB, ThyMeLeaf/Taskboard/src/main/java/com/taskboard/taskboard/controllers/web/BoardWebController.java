package com.taskboard.taskboard.controllers.web;

import com.taskboard.taskboard.entities.Board;
import com.taskboard.taskboard.entities.Category;
import com.taskboard.taskboard.entities.Task;
import com.taskboard.taskboard.services.BoardService;
import com.taskboard.taskboard.services.CategoryService;
import com.taskboard.taskboard.services.TaskService;
import com.taskboard.taskboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/boards")
public class BoardWebController {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TaskService taskService;

    private static final Long USER_ID = 1L;


    // Muestra detalles de tablero
    @GetMapping("/{boardId}")
    public String showBoard(@PathVariable Long boardId, Model model) {
        Map<Category, List<Task>> categoryTaskMap = categoryService.getTasksByCategory(boardId);

        model.addAttribute("board", boardService.getBoardById(boardId));
        model.addAttribute("categoryTaskMap", categoryTaskMap);

        return "board";
    }

    // Mostrar formulario de creación
    @GetMapping("/add")
    public String showCreateForm() {
        return "board-add";
    }

    @GetMapping("/{boardId}/update")
    public String showUpdateForm(@PathVariable("boardId") Long boardId, Model model) {
        model.addAttribute("board", boardService.getBoardById(boardId));
        model.addAttribute("categoryCount", categoryService.getCategoriesCountByBoardId(boardId));
        model.addAttribute("taskCount", taskService.getTasksCountByBoardId(boardId));

        return "board-update";
    }

    @GetMapping("/{boardId}/delete")
    public String deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId, USER_ID);

        return "redirect:/";
    }

    // Procesar la creación del tablero
    @PostMapping("/add")
    public String createBoard(@RequestParam("name") String name,
                              @RequestParam(value ="description", required = false) String description,
                              RedirectAttributes redirectAttributes) {
        try {
            Board board = new Board();
            board.setName(name);
            board.setDescription(description);
            board.setUser(userService.getUserById(USER_ID));

            boardService.createBoard(board);

            redirectAttributes.addFlashAttribute("message", "Tablero creado correctamente");
            return "redirect:/";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/boards/add";
        }
    }

    @PostMapping("/{id}/update")
    public String updateBoard(@PathVariable("id") Long id,
                              @RequestParam("newName") String newName,
                              @RequestParam(value = "newDescription", required = false) String newDescription,
                              RedirectAttributes redirectAttributes) {
        try {
            boardService.updateBoardName(id, USER_ID, newName);
            boardService.updateBoardDescription(id, USER_ID, newDescription);
            redirectAttributes.addFlashAttribute("message", "Tablero actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/boards/" + id + "/update";
    }

}
