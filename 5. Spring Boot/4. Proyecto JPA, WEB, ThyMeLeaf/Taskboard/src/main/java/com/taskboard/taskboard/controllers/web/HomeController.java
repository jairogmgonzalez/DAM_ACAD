package com.taskboard.taskboard.controllers.web;

import com.taskboard.taskboard.entities.Board;
import com.taskboard.taskboard.entities.User;
import com.taskboard.taskboard.services.BoardService;
import com.taskboard.taskboard.services.CategoryService;
import com.taskboard.taskboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    private static final Long USER_ID = 1L;

    @GetMapping("/")
    public String showHome(Model model) {
        User user = userService.getUserById(USER_ID);
        List<Board> boards = boardService.getBoardsByUserId(USER_ID);

        model.addAttribute("user", user);
        model.addAttribute("boards", boards);

        return "home";
    }

}
