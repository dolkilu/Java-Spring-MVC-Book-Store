package hkmu.comps380f.s1326557_project.controller;

import hkmu.comps380f.s1326557_project.model.Customer;
import hkmu.comps380f.s1326557_project.model.RoleOption;
import hkmu.comps380f.s1326557_project.repository.CustomerRepository;
import hkmu.comps380f.s1326557_project.service.*;
import hkmu.comps380f.s1326557_project.model.Forms;
import hkmu.comps380f.s1326557_project.validator.UserValidator;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final UserManagementService userManagementService;
    private final UserService userService;
    private final AdminService adminService;
    private final CountryService countryService;
    private final UserValidator userValidator;
    private final CustomerRepository customerRepository;

    public AdminController(UserManagementService userManagementService, UserService userService, AdminService adminService, CountryService countryService, UserValidator userValidator, CustomerRepository customerRepository) {
        this.userManagementService = userManagementService;
        this.userService = userService;
        this.adminService = adminService;
        this.countryService = countryService;
        this.userValidator = userValidator;
        this.customerRepository = customerRepository;
    }


    @GetMapping("/manageUsers")
    public String listUser(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @PageableDefault(size = 25) Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);
        model.addAttribute("pageTitle", "Manage Users");
        model.addAttribute("users", customers.getContent());
        model.addAttribute("size", 25);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customers.getTotalPages());
        return "manageUsers";
    }

    @GetMapping("/addUser")
    public ModelAndView addUser(Model model) {
        model.addAttribute("pageTitle", "Create User");
        model.addAttribute("countries", countryService.getCountries());
        model.addAttribute("roles", Map.of("ROLE_USER", "User", "ROLE_ADMIN", "Admin"));
        return new ModelAndView("addUser", "addUser", new Forms.RegisterForm());
    }

    @PostMapping("/addUser")
    public String register(@ModelAttribute("addUser") @Valid Forms.RegisterForm form, BindingResult result, Model model) throws IOException {
        userValidator.validate(form,result);
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Create User");
            model.addAttribute("countries", countryService.getCountries());
            model.addAttribute("roles", Map.of("ROLE_USER", "User", "ROLE_ADMIN", "Admin"));
            return "addUser";
        }
        try {
            userManagementService.createUserAndCustomer(
                    form.getUsername(),form.getPassword(), form.getRoles(),
                    form.getFirstname(), form.getLastname(),
                    form.getEmail(), form.getPhone(),
                    form.getCountry(), form.getCity(),
                    form.getAddress1(), form.getAddress2(), form.getPostalCode());
            logger.info("User {} created.", form.getUsername());
            return "redirect:/admin/userInfo/" + form.getUsername();
        } catch (Exception e) {
            model.addAttribute("pageTitle", "Create User");
            model.addAttribute("countries", countryService.getCountries());
            model.addAttribute("roles", Map.of("ROLE_USER", "User", "ROLE_ADMIN", "Admin"));
            return "addUser";
        }
    }

    @PostMapping("/deleteUser/{username}")
    public String deleteUser(@PathVariable("username") String username, RedirectAttributes ra) {
        try {
            adminService.deleteUser(username);
            logger.info("Deleted User {}", username);
            ra.addFlashAttribute("success", true);
            ra.addFlashAttribute("message", "Successfully delete User " + username);
            return "redirect:/admin/manageUsers";
        } catch (Exception e) {
            return "redirect:/admin/userInfo/" + username;
        }
    }

    @GetMapping("/userInfo/{username}")
    public ModelAndView viewUser(@PathVariable("username") String username)  {
        ModelAndView mav = new ModelAndView("userInfo");

        List<RoleOption> rolesOptions = Stream.of(
                new RoleOption("ROLE_USER", "User"),
                new RoleOption("ROLE_ADMIN", "Admin")
        ).toList();

        Forms.RoleForm roleForm = new Forms.RoleForm();
        roleForm.setRoles(adminService.getRoles(username));
        Forms.CustomerForm customerForm = userService.createCustomerInfoForm(username);

        mav.addObject("rolesOptions", rolesOptions);
        mav.addObject("customerForm", customerForm);
        mav.addObject("roleForm", roleForm);
        mav.addObject("username", username);
        mav.addObject("countries", countryService.getCountries());
        mav.addObject("pageTitle", username + " 's Info");
        return mav;
    }

    @PostMapping("/userInfo/{username}/updateUser")
    public String updateUser(@ModelAttribute("customerInfo") @Valid Forms.CustomerForm form,
                             BindingResult result,
                             RedirectAttributes ra, @PathVariable("username") String username)
            throws UsernameNotFoundException {
        if (result.hasErrors()) {
            ra.addFlashAttribute("error", true);
            ra.addFlashAttribute("message", "Failed to update form");
            return "redirect:/admin/userInfo/" + username;
        }
        adminService.updateUserInfo(username,
                form.getFirstname(), form.getLastname(),
                form.getEmail(), form.getPhone(),
                form.getCountry(), form.getCity(),
                form.getAddress1(), form.getAddress2(), form.getPostalCode());
        ra.addFlashAttribute("success", true);
        ra.addFlashAttribute("message", "Successfully update " + username);
//        return "userInfo";
        return "redirect:/admin/userInfo/" + username;
    }

    @PostMapping("/userInfo/{username}/updateRole")
    public String updateRole(@PathVariable("username") String username,
                             @ModelAttribute("roleForm") @Valid Forms.RoleForm form,
                             BindingResult result, RedirectAttributes ra) throws UsernameNotFoundException {
        try {
            if (result.hasErrors()) {
                ra.addFlashAttribute("error", true);
                ra.addFlashAttribute("message", "Failed to update role");
                return "redirect:/admin/userInfo/" + username;
            }
            adminService.updateRole(username, form.getRoles());
            return "redirect:/admin/userInfo/" + username;
        } catch (UsernameNotFoundException e) {
            return "index";
        }
    }

}