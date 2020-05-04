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

import com.ggrecipes.model.*;

import java.util.*;

@RestController
public class GreetingController {
	private GenerateRecipes generateRecipes;
	private GetRecipes getRecipies;
	@GetMapping("/")
	public GenerateRecipes test() throws IOException{
		ArrayList<String> ingrList=new ArrayList<String>();
		ingrList.add("oeuf");
		generateRecipes= new GenerateRecipes();
		getRecipes= new GetRecipes(ingrList);
		generateRecipes.Recipe("https://www.marmiton.org/recettes/recette_risotto-aux-crevettes-et-pointes-d-asperges_31554.aspx");
		Response response = new Response();
		//response.status = 200;
		//response.recettes = generateRecipes.getListRecettes();
		//return response;
		return generateRecipes;

	}
	/*
	@GetMapping("/getRecipe")
	public Response getRecipe(Recette recette){

		Response response = new Response();
		response.status = 200;
		response.recettes.add(generateRecipes.Recipe(recette.MarmittonURL))
	}
	*/

}
