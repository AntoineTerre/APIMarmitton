package com.ggrecipes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.ggrecipes.model.GenerateRecipes;

import java.util.*;

@RestController
public class GreetingController {
	private GenerateRecipes generateRecipes;

	@GetMapping("/")
	public GenerateRecipes test() throws IOException{
		ArrayList<String> ingrList=new ArrayList<String>();
		ingrList.add("oeuf");
		generateRecipes= new GenerateRecipes(ingrList);
		generateRecipes.Recipe("https://www.marmiton.org/recettes/recette_risotto-aux-crevettes-et-pointes-d-asperges_31554.aspx");
		return generateRecipes;

	}

}
