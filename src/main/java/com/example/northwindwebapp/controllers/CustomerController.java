package com.example.northwindwebapp.controllers;

import com.example.northwindwebapp.entities.Customer;
import com.example.northwindwebapp.entities.Order;
import com.example.northwindwebapp.repositories.CustomerRepository;
import com.example.northwindwebapp.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {
	enum FIELD_NAMES {
		CUSTOMER_ID("Customer ID"), COMPANY_NAME("Company Name"), CONTACT_NAME("Contact Name"), CONTACT_TITLE("Contact Title"), ADDRESS("Address"), CITY("City"), REGION("Region"), POST_CODE("Post Code"), COUNTRY("Country"), PHONE_NUMBER("Phone Number"), FAX("Fax");

		private final String name;

		FIELD_NAMES(String name) {
			this.name = name;
		}
	}

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	OrderRepository orderRepository;


	@GetMapping("/customers")
	public String getAll(Model model) {
		List<Customer> Customers = customerRepository.findAll();
		List<List<String>> customerList = new ArrayList<>();
		for (Customer c : Customers) {
			customerList.add(createCustomerInfoList(c));
		}
		model = prepareDataModel(model, customerList);
		return "data";
	}

	@GetMapping("/customers/{id}/edit")
	public String displayUpdateCustomer(@PathVariable String id, Model model) {
		Optional<Customer> result = customerRepository.findById(id);
		Customer customer = null;
		ArrayList<Customer> customers = null;
		List<String> customerData;
		List<List<String>> customerList = new ArrayList<>();
		if (result.isPresent()) {
			customer = result.get();
			customerData = createCustomerInfoList(customer);
			customerList.add(customerData);
		} else {
			model.addAttribute("missing", "Customer not found");
			return "missing";
		}
		model.addAttribute("customer", customer);

		return "customer/updateCustomer";
	}

	@PostMapping("/customers/{id}/edit")
	public String updateCustomer(@ModelAttribute("customer") Customer customer) {
		customerRepository.save(customer);
		return "data";
	}

	@GetMapping("/customers/create")
	public String displayCreateCustomer(Model model) {
		model.addAttribute("customer", new Customer());
		return "customer/createCustomer";
	}

	@PostMapping("/customers/create")
	public String createCustomer(@ModelAttribute("customer") Customer customer) {
		customerRepository.save(customer);
		return "data";
	}

	@PostMapping("customers/{id}/delete")
	public String deleteCustomer(@PathVariable String id) {
		Optional<Customer> optionalCustomer = customerRepository.findById(id);
		if (optionalCustomer.isPresent()){
			Customer foundCustomer = optionalCustomer.get();
			List<Order> orders = orderRepository.findAllByCustomerID(foundCustomer);
			for (Order order: orders) {
				order.setCustomerID(null);
			}
			orderRepository.saveAll(orders);
			customerRepository.deleteById(foundCustomer.getId());
			return "data";
		}else return "missing";
	}


	@GetMapping("/customers/{id}")
	public String getbyId(Model model, @PathVariable String id) {
		Optional<Customer> result = customerRepository.findById(id);
		Customer customer = null;
		ArrayList<Customer> customers = null;
		List<String> customerData;
		List<List<String>> customerList = new ArrayList<>();
		if (result.isPresent()) {
			customer = result.get();
			customerData = createCustomerInfoList(customer);
			customerList.add(customerData);
		} else {
			model.addAttribute("missing", "Customer not found");
			return "missing";
		}
		model = prepareDataModel(model, customerList);
		return "data";

	}

	private List<String> createCustomerInfoList(Customer customer) {
		List<String> customerData = new ArrayList<>();
		customerData.add(String.valueOf(customer.getId()));
		customerData.add(String.valueOf(customer.getCompanyName()));
		customerData.add(String.valueOf(customer.getContactName()));
		customerData.add(String.valueOf(customer.getContactTitle()));
		customerData.add(String.valueOf(customer.getAddress()));
		customerData.add(String.valueOf(customer.getCity()));
		customerData.add(String.valueOf(customer.getRegion()));
		customerData.add(String.valueOf(customer.getPostalCode()));
		customerData.add(String.valueOf(customer.getCountry()));
		customerData.add(String.valueOf(customer.getPhone()));
		customerData.add(String.valueOf(customer.getFax()));
		return customerData;
	}

	private Model prepareDataModel(Model model, List<List<String>> customerList) {
		model.addAttribute("title", "Customer");
		model.addAttribute("fieldNames", FIELD_NAMES.values());
		model.addAttribute("entityList", customerList);
		model.addAttribute("source", "customers");
		return model;
	}

}
