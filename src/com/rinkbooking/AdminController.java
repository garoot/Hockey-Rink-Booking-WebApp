package com.rinkbooking;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rinkbooking.entity.Admin;

@Controller 
public class AdminController {
	//returning JSP page for admin registration form
	@RequestMapping("/adminRegisterForm")
	public String showAdminRegisterForm() {
		return "admin-register-form";
	}
	
	//processing admin registration entries, returning successful registration page 
	@RequestMapping("/processAdminRegisterForm")
	public String processAdminRegisterForm(@RequestParam("userName") String userName, @RequestParam("password") String password) {
		System.out.println("Creating new admin object...");
		Admin newAdmin = new Admin(userName, password);
		
			
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Admin.class)
								.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
				
		try {
			session.beginTransaction();
			
			System.out.println("Saving the admin...");
			session.save(newAdmin);
			
			session.getTransaction().commit();
			System.out.println("Done!");
		}
		finally {
			factory.close();
		}
		
		return "admin-register-success";
	}
	
	//returning admin sign in page
	@RequestMapping("/adminLoginForm")
	public String showAdminLoginForm() {
		return "admin-login-form";
	}
	
	//returns a page with all existing bookings
	@RequestMapping("/adminShowAllBookings")
	public String showAllBookings() {
		return "all-bookings";
	}
	
	//returns admin's home
	@RequestMapping("/adminHome")
	public String adminHome() {
		return "admin-home";
	}
	
	//processing admin login data
	@RequestMapping("/processAdminLoginForm")
	public String processAdminLoginForm(@RequestParam("userName") String userName, @RequestParam("password") String password) {
		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Admin.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			// retrieve group based on the username: primary key
			System.out.println("Getting admin with username: " + userName);
			
			Admin loggedInAdmin = session.get(Admin.class, userName);
			
			System.out.println("Get complete: " + loggedInAdmin);
			
			// commit the transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
			
			if(password.equals(loggedInAdmin.getPassword())) {
				return "admin-home";
			} else {
				return "admin-login-failed";
			}
		}
		finally {
			factory.close();
		}
	}

}
