package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private UserService userService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/change-user/{id}")
    public String changeUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id)); // Передаем одного пользователя
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "change-user";
    }

    @PostMapping("/update-user/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User updatedUser, @RequestParam("roles") List<Long> roleIds) {
        Set<Role> newRoles = roleService.getRolesById(roleIds);
        updatedUser.setRoles(newRoles);
        userService.editUser(id, updatedUser);
        return "redirect:/admin";
    }


}
