package hkmu.comps380f.s1326557_project.controller;

import hkmu.comps380f.s1326557_project.service.*;
import hkmu.comps380f.s1326557_project.model.*;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final UserService userService;
    private final CountryService countryService;

    public UserManagementController(UserManagementService userManagementService, UserService userService, CountryService countryService) {
        this.userManagementService = userManagementService;
        this.userService = userService;
        this.countryService = countryService;
    }

    @GetMapping("/changePassword")
    public ModelAndView changePassword(Model model) {
        model.addAttribute("pageTitle", "Change Password");
        return new ModelAndView("changePassword", "password", new Forms.PasswordForm());
    }

    @PostMapping("/changePassword")
    public String changePassword (@ModelAttribute("password") @Valid Forms.PasswordForm form, BindingResult result,
                                  RedirectAttributes ra,Model model) throws UsernameNotFoundException {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Change Password");
            return "changePassword";
        }
        try {
            userManagementService.updatePassword(form, result);
            if (result.hasErrors()) {
                model.addAttribute("pageTitle", "Change Password");
                ra.addFlashAttribute("error", true);
                ra.addFlashAttribute("message", "Failed  to change password");
                return "changePassword";
            }
            ra.addFlashAttribute("success", true);
            ra.addFlashAttribute("message", "Successfully change password.");
            return "redirect:/user/changePassword";
        } catch (Exception e) {
            ra.addFlashAttribute("error", true);
            ra.addFlashAttribute("errorMessage", e);
            return "changePassword";
        }
    }

    @GetMapping("/updateCustomerInfo")
    public ModelAndView getCustomerInfo(Model model) throws UsernameNotFoundException{
        model.addAttribute("pageTitle", "Update User Info");
        model.addAttribute("countries", countryService.getCountries());
        Forms.CustomerForm customerForm = userService.createCustomerInfoForm(userService.getCurrentUserUsername());
        return new ModelAndView("updateCustomerInfo", "customerInfo", customerForm);
    }


    @PostMapping("/updateCustomerInfo")
    public String updateCustomerInfo(@ModelAttribute("customerInfo") @Valid Forms.CustomerForm form, BindingResult result,
                                     RedirectAttributes ra,Model model)
            throws UsernameNotFoundException{
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Update User Info");
            model.addAttribute("countries", countryService.getCountries());
            return "updateCustomerInfo"; }
        userManagementService.updateCustomerInfo(
                form.getFirstname(), form.getLastname(),
                form.getEmail(), form.getPhone(),
                form.getCountry(), form.getCity(),
                form.getAddress1(), form.getAddress2(), form.getPostalCode());
        ra.addFlashAttribute("success", true);
        ra.addFlashAttribute("message", "Successfully update personal info.");
        return "redirect:/user/updateCustomerInfo";
    }

}
