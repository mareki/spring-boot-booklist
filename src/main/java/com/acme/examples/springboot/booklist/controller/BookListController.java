package com.acme.examples.springboot.booklist.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acme.examples.springboot.booklist.domain.BookEntity;
import com.acme.examples.springboot.booklist.repository.BookRepository;

@Controller
@RequestMapping("/")
public class BookListController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookListController.class);
	
	@Autowired
	private BookRepository bookRepository;
	
	@RequestMapping(value = "/booklist/{reader}", method = RequestMethod.GET)
	public String readersBooks(@PathVariable("reader") String reader, Model model) {
		
		LOGGER.debug("listing books for reader: {0}", reader);
		
		List<BookEntity> books = bookRepository.findByReader(reader);
		
		if(books != null) {
			model.addAttribute("books", books);
		}
		
		return "booklist";
	}
	
	@RequestMapping(value = "/booklist/{reader}", method = RequestMethod.POST)
	public String addToReadingList(@PathVariable("reader") String reader, BookEntity book) {
		
		LOGGER.debug("adding new book for reader: {0}", reader);
		
		book.setReader(reader);
		bookRepository.save(book);
		
		return "redirect:/booklist/{reader}";
	}
	
}
