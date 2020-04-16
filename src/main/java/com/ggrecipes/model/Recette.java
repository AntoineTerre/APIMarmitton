package com.ggrecipes.model;

import java.io.IOException;
import java.util.ArrayList;
public class Recette {

	public String ingredients;
	public ArrayList<String> steps;
	public String name;
	public ArrayList<String> tags;
	public ArrayList<String> nb_comments;
	public String cook_time;

	public Recette(String ingredients,ArrayList<String> steps,String name,ArrayList<String> tags,ArrayList<String> nb_comments,String cook_time) {
		this.ingredients=ingredients;
		this.steps=steps;
		this.name=name;
		this.tags=tags;
		this.nb_comments=nb_comments;
		this.cook_time=cook_time;
	}

} 