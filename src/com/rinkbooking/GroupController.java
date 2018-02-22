package com.rinkbooking;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rinkbooking.entity.Booking;
import com.rinkbooking.entity.Group;

@Controller 
public class GroupController {
	Group loggedInGroup;
	
	//returns a group registration form
	@RequestMapping("/groupRegisterForm")
	public String showGroupRegisterForm() {
		return "group-register-form";
	}
	
	//processing group registration data
	@RequestMapping("/processGroupRegisterForm")
	public String processGroupRegisterForm(
			@RequestParam("firstName") String firstName, 
			@RequestParam("lastName")  String lastName, 
			@RequestParam("country")   String country, 
			@RequestParam("address")   String address, 
			@RequestParam("state")     String state, 
			@RequestParam("zipcode")   String zipcode, 
			@RequestParam("userName")  String userName, 
			@RequestParam("password")  String password, 
			@RequestParam("creditCardNum") String creditCardNum, 
			@RequestParam("secCode")   String secCode) {
		
		System.out.println("Creating new group object...");
		Group newGroup = new Group(firstName, lastName, country, address, state, zipcode, userName, password, creditCardNum, secCode);
		
			
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Group.class)
								.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
				
		try {
			session.beginTransaction();
			
			System.out.println("Saving the group...");
			session.save(newGroup);
			
			session.getTransaction().commit();
			System.out.println("Done!");
		}
		finally {
			factory.close();
		}
		
		return "group-register-success";
	}
	
	//returns group login form
	@RequestMapping("/groupLoginForm")
	public String showGroupLoginForm() {
		return "group-login-form";
	}

	//processing group login data
	@RequestMapping("/processGroupLoginForm")
	public String processGroupLoginForm(@RequestParam("userName") String userName, @RequestParam("password") String password) {
		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Group.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			// retrieve group based on the username: primary key
			System.out.println("Getting group with username: " + userName);
			
			loggedInGroup = session.get(Group.class, userName);
			
			System.out.println("Get complete: " + loggedInGroup);
			
			// commit the transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
			
			if(password.equals(loggedInGroup.getPassword())) {
				return "group-home";
			} else {
				return "group-login-failed";
			}
		}
		finally {
			factory.close();
		}
	}
	
	//returns group home page 
	@RequestMapping("/groupHome")
	public String groupHome() {
		return "group-home";
	}
	
	//returns a page to pick rink number
	@RequestMapping("/chooseRinkForm")
	public String chooseRinkForm() {
		return "choose-rink-form";
	}

	//processing the chosen rink and display related bookings
	@RequestMapping("/processRinkChoosingForm")
	public String processRinkChoosingForm() {
		return "booking-form";
	}
	
	//processing booking entered data
	@RequestMapping("/processBookingForm")
	public String processBookingForm(@RequestParam("rinkNum") int rinkNum, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("date") String date) {
		System.out.println("Creating new booking object...");
		Booking newBooking = new Booking(0, rinkNum, loggedInGroup.getUsername(), startTime, endTime, date);
		String[] endTimeParts = endTime.split(":");
		String maintenanceEndTime;
		if(Integer.parseInt(endTimeParts[1]) == 30) {
			maintenanceEndTime = (Integer.parseInt(endTimeParts[0]) + 1) + ":00";
		} else {
			maintenanceEndTime = (Integer.parseInt(endTimeParts[0])) + ":30";
		}
		
		Booking newBookingMaintenance = new Booking(0, rinkNum, "Maintenance", endTime, maintenanceEndTime, date);
		
			
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Booking.class)
								.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
				
		try {
			session.beginTransaction();
			
			
			// Create CriteriaBuilder
	        CriteriaBuilder builder = session.getCriteriaBuilder();
	 
	        // Create CriteriaQuery
	        CriteriaQuery<Booking> criteria = builder.createQuery(Booking.class);
			
	    	 	// Specify criteria root
	        criteria.from(Booking.class);
	       
	        // Execute query
	        List<Booking> bookingList = new ArrayList<Booking>();
	        bookingList = session.createQuery(criteria).getResultList();
	        
	        String[] newPartsStartTime;
	        String[] partsStartTime;
	        String[] newPartsEndTime;
	        String[] partsEndTime;
	        
	        int newStartTime;
	        int oldStartTime;
	        int newEndTime;
	        int oldEndTime;
	        
	        boolean goodBooking = true;
	        
	        newPartsStartTime = newBooking.getStartTime().split(":");
			newPartsEndTime = newBooking.getEndTime().split(":");
			newStartTime = Integer.parseInt(newPartsStartTime[0] + newPartsStartTime[1]);
			newEndTime = Integer.parseInt(newPartsEndTime[0] + newPartsEndTime[1]);
			
			if(newStartTime >= newEndTime) {
    				goodBooking = false;
    			}

			if(goodBooking == true) {
		        for(Booking booking : bookingList) {
			      
		    			partsStartTime = booking.getStartTime().split(":");
		    			partsEndTime = booking.getEndTime().split(":");
		    			
		    			oldStartTime = Integer.parseInt(partsStartTime[0] + partsStartTime[1]);
		    			oldEndTime = Integer.parseInt(partsEndTime[0] + partsEndTime[1]);
		        		
		        		if(booking.getRinkNum() == newBooking.getRinkNum() && booking.getDate().equals(newBooking.getDate())){
		        			System.out.println("Checking rinkNum");
		        		
		        			if(newStartTime < oldStartTime && newEndTime > oldStartTime) {
		        				goodBooking = false;
		        			} else if(newStartTime > oldStartTime && newStartTime < oldEndTime) {
		        				goodBooking = false;
		        			} else if(newStartTime == oldStartTime) {
		        				goodBooking = false;
		        			} else if (newEndTime == oldEndTime) {
		        				goodBooking = false;
		        			}
		        			
		        		}  
		        }
			}
			
	        if(goodBooking == true) {
				System.out.println("Saving the booking...");
				session.save(newBooking);
				session.save(newBookingMaintenance);
				
				session.getTransaction().commit();
				System.out.println("Done!");
	        } else {
	        		return "booking-failed";
	        }
		}
		finally {
			factory.close();
		}
		
		return "booking-success";	
	
	}
	
	//returns a page with the logged-in group's bookings
	@RequestMapping("/showGroupBookings")
	public String showGroupBookings() {
		return "group-bookings";
	}

	//returns the logged-in group's bills
	@RequestMapping("/showGroupBills")
	public String showGroupBills() {
		return "group-bills";
	}
}
