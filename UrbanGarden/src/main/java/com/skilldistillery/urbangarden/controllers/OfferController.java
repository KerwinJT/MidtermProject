package com.skilldistillery.urbangarden.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.skilldistillery.urbangarden.data.OfferDAO;
import com.skilldistillery.urbangarden.entities.Offer;
import com.skilldistillery.urbangarden.entities.User;

@Controller
public class OfferController {
		
	@Autowired
	private OfferDAO dao;
	
	@RequestMapping(path = "getOffer.do", method = RequestMethod.GET, params = "id")
	public String getOffer(@RequestParam Integer id, Model model) {
		String view = "showOffer";
		Offer offer = dao.findById(id);
		model.addAttribute("offer", offer);
		return view;
	}
	
	@RequestMapping(path = "addOffer.do", method = RequestMethod.GET)
	public String addTrade( Model model, Offer offer) {
		String view = "addOffer";
		model.addAttribute("offer", offer);
		return view;
	}
	
	@RequestMapping(path = "addOffer.do", method = RequestMethod.POST)
	public String postOffer(Model model, Offer offer) {
		String view = "offerPost";
		dao.create(offer);
		return view;
	}
	
	@RequestMapping(path = "deactivateOffer.do", method = RequestMethod.POST, params="id")
	public String deactivateOffer(@RequestParam Integer id, HttpSession session, Model model) {
		Offer offer = dao.deactivate(id);
//		model.addAttribute("offer", offer);
//		model.addAttribute("id", id);
		System.out.println((User) session.getAttribute("userSession"));
		model.addAttribute("user", dao.findById(((User) session.getAttribute("userSession")).getId()));
		return "myGardenStoreFront";
	}
	
	@RequestMapping(path = "deleteOffer.do", method = RequestMethod.POST, params="id")
	public String deleteOffer(@RequestParam Integer id, HttpSession session, Model model) {
		boolean deleted = dao.delete(id);
		model.addAttribute("delete", deleted);
		model.addAttribute("id", id);
		model.addAttribute("user", dao.findById(((User) session.getAttribute("userSession")).getId()));
		return "myGardenStoreFront";
	}
	
	@RequestMapping(path = "editOffer.do", method = RequestMethod.GET)
	public String editOffer(@RequestParam Integer id, Model model) {
		String view = "editOffer";
		model.addAttribute("offer", dao.findById(id));
		return view;
	}
	
	@RequestMapping(path = "editOffer.do", method = RequestMethod.POST)
	public String updateOffer(@RequestParam Integer id, @RequestParam Offer offer, Model model) {
		String view = "homePage";
//		model.addAttribute("offer", offer);
		dao.update(id, offer);
		return view;
	}
	
	@RequestMapping(path = "showOffer.do")
	public String showOffer(@RequestParam Integer id, Model model, HttpSession session) {
		String view = "showOffer";
		Offer offer = dao.findById(id);
		model.addAttribute("offer", offer);
		return view;
	}
	
}
