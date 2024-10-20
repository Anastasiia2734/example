package web.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.model.User;
import web.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping({"", "/", "list"})
    public String showAllUsers(Model model, @ModelAttribute("flashMessage") String flashAttribute) {
        model.addAttribute("users", userService.getAllUsers());
    return "list";
    }

    @GetMapping("/new")
    public String addUserForm(@ModelAttribute("user")User user) {
        return "form";
    }

    @GetMapping("/{id}/edit")
    public String edidtUserForm(@PathVariable(value = "id", required = true) long id, Model model,
                                RedirectAttributes attributes) {
        User user = userService.readUser(id);

        if (null == user) {
            attributes.addFlashAttribute("flashMessage", "User are not exists!");
            return "redirect:/users";
        }

        model.addAttribute("user", userService.readUser(id));
        return "form";
    }
    @PostMapping()
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                           RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        userService.createOrUpdateUser(user);
        attributes.addFlashAttribute("flashMessage",
                "User " + user.getName() + " successfully created!");
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam(value = "id", required = true, defaultValue = "") long id,
                             RedirectAttributes attributes) {
        User user = userService.deleteUser(id);

        attributes.addFlashAttribute("flashMessage", (null == user) ?
                "User are not exists!" :
                "User " + user.getName() + " successfully deleted!");

        return "redirect:/users";
    }

}
