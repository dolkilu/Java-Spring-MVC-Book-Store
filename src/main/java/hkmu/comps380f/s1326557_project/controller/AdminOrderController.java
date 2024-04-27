package hkmu.comps380f.s1326557_project.controller;

import hkmu.comps380f.s1326557_project.model.BookUser;
import hkmu.comps380f.s1326557_project.model.Order;
import hkmu.comps380f.s1326557_project.model.OrderDTO;
import hkmu.comps380f.s1326557_project.repository.OrderRepository;
import hkmu.comps380f.s1326557_project.repository.UserRepository;
import hkmu.comps380f.s1326557_project.service.AdminService;
import hkmu.comps380f.s1326557_project.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {
    private final OrderService orderService;
    private final AdminService adminService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public AdminOrderController(OrderService orderService, AdminService adminService, UserRepository userRepository, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/listOrders")
    public String viewAllOrders(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @PageableDefault(size = 25) Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        model.addAttribute("pageTitle", "Manage Users");
        model.addAttribute("orders", orders.getContent());
        model.addAttribute("size", 25);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());
        return "listOrders";
    }

    @GetMapping("/viewOrders/user/{username}")
    public String viewUserOrders(Model model,
                                 @PathVariable("username") String username,
                                 @PageableDefault(size = 3) Pageable pageable,
                                 @RequestParam(name = "page", defaultValue = "0") int page) {
        BookUser user = userRepository.findBookUserByUsername(username);
        Page<OrderDTO> orderDTOs = orderService.getUserOrders(user, pageable);

        model.addAttribute("pageTitle", "Order History");
        model.addAttribute("orderDTOs", orderDTOs);
        model.addAttribute("username", username);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderDTOs.getTotalPages());
        model.addAttribute("adminView",true);
        return "viewOrders";
    }

    @GetMapping("/viewOrders/order/{orderId}")
    public String viewOrder(Model model, @PathVariable ("orderId") long id) {
        OrderDTO orderDTO = orderService.getOrder(id);
        System.out.println(orderDTO.getOrder());
        System.out.println(orderDTO.getOrderBooks());
        model.addAttribute("pageTitle", "Order " + id);
        model.addAttribute("username", orderDTO.getOrder().getUser().getUsername());
        model.addAttribute("orderDTO", orderDTO);
        return "viewOrder";
    }

    @PostMapping("/viewOrders/{orderId}/delete")
    public String deleteOrder(@PathVariable ("orderId") long id, RedirectAttributes ra) {
        try {
            ra.addFlashAttribute("success", true);
            ra.addFlashAttribute("message", "Successfully delete order.");
            adminService.deleteOrder(id);
            return "redirect:/admin/listOrders";
        } catch (Exception e) {
            ra.addFlashAttribute("error", true);
            ra.addFlashAttribute("message", "Failed to delete order.");
            return "redirect:/admin/listOrders" ;
        }
    }
}
