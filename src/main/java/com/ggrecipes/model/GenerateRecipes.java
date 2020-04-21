package com.ggrecipes.model;

import java.util.List; // import just the List interface
import java.util.ArrayList; // import just the ArrayList class
import java.util.Map; // import just the List interface
import java.util.HashMap;
//import org.junit.Assert;
//import static org.junit.Assert.*;
//import org.junit.Test;
import java.util.regex.*;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.StringWebResponse;

import org.apache.commons.io.FileUtils;
//import org.junit.Before;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.io.*;

//import static org.junit.Assert.assertNotNull;

public class GenerateRecipes {
    //public List<String> ingredients = new ArrayList<String>();
    public List<String> steps = new ArrayList<String>();
    public String title;
    public String temps;
    public Float personnes;
    public String difficulte;
    public String cout;
    public List<String> ustensiles = new ArrayList<String>();
    public int status = 200;
    public Map<String,Float> ingredients = new HashMap<>();
    
    private final String URLMarimitton = "https://www.marmiton.org/recettes/recherche.aspx";

    public void Recipe(String url) throws IOException {

    	WebClient webClient=webClientCreator();

        try {
            HtmlPage page = (HtmlPage) webClient.getPage(url);
            status = page.getWebResponse().getStatusCode();
            List<HtmlElement> ingredientsHtml = page.getByXPath("//span[(@class='ingredient')]");
            List<HtmlElement> quantitéHtml = page.getByXPath("//span[contains(@class, 'recipe-ingredient-qt')]");
            for (int i = 0; i < ingredientsHtml.size(); i++) {
                try {
                    ingredients.put(ingredientsHtml.get(i).asText(), Float.parseFloat(quantitéHtml.get(i).asText()));
                } catch (Exception e) {
                    ingredients.put(ingredientsHtml.get(i).asText(), null);
                }

            }
            List<HtmlElement> stepsHtml = page.getByXPath("//li[contains(@class, 'recipe-preparation__list')]");
            for (HtmlElement i : stepsHtml) {
                steps.add(i.asText());
            }
            List<HtmlElement> ustensilesHtml = page.getByXPath("//span[contains(@class, 'recipe-utensil__name')]");
            for (HtmlElement i : ustensilesHtml) {
                ustensiles.add(i.asText().substring(0,i.asText().length()-2));
            }
            List<HtmlElement> tempsHtml = page
                    .getByXPath("//span[contains(@class, 'title-2 recipe-infos__total-time__value')]");
            temps = tempsHtml.get(0).asText();
            List<HtmlElement> personnesHtml = page
                    .getByXPath("//span[contains(@class, 'title-2 recipe-infos__quantity__value')]");
            personnes = Float.parseFloat(personnesHtml.get(0).asText());
            List<HtmlElement> difficulteHtml = page.getByXPath("//div[(@class='recipe-infos__level')]");
            try {
                difficulte = difficulteHtml.get(0).asText();
            } catch (Exception e) {
                difficulte = "";
            }
            List<HtmlElement> coutHtml = page.getByXPath("//div[(@class='recipe-infos__budget')]");
            try {
                cout = coutHtml.get(0).asText();
            } catch (Exception e) {
                cout = "";
            }
            List<HtmlElement> titleHtml = page.getByXPath("//h1[(@class='main-title ')]");
            title = titleHtml.get(0).asText();


        } catch (Exception e) {
            status = 404;
        }
      //protection for memory leak
        webClient.close();
    }

    public GenerateRecipes(Map<String,Float> ingredients, ArrayList<String> steps,ArrayList<String> ustensiles, String title, String temps,Float personnes,String difficulte,String cout) {
        this.ingredients = ingredients;
        this.steps = steps;
        this.title = title;
        this.temps = temps;
        this.personnes = personnes;
        this.difficulte = difficulte;
        this.cout = cout;
        this.ustensiles = ustensiles;
    }

    public GenerateRecipes(ArrayList<String> ingredientRecette) {
   	 String urlMarmittonRecherche=URLBuilder(ingredientRecette);
   	 String start="&start=";
   	 String page ="&page=";
   	
   	int nbrsElementParPage=15;//default value
   	int nbrsElementInt=0;
   	int compteurRecipiesExtract=0;
   	
   	 Boolean boolInitialisation=false;
   	 //----------------------Initialisation with the first page----------------------------
   	 WebClient webClient=webClientCreator();
   	 try {
   		 HtmlPage htmlMarmitton = (HtmlPage) webClient.getPage(urlMarmittonRecherche);
   		 status = htmlMarmitton.getWebResponse().getStatusCode();
   		
   		 List<HtmlElement> nombreElement = htmlMarmitton.getByXPath("//span[(@class='recipe-search__nb-results')]");
   		 String nbrsElementStringResultat=nombreElement.get(0).asText();
   		 String SEPARATEUR = " ";
   		 String separateurTab[] = nbrsElementStringResultat.split(SEPARATEUR);
   		 String nbrsElementString=separateurTab[0];
   		 nbrsElementInt = Integer.parseInt(nbrsElementString);
   		 
   		List<HtmlElement> recipies = htmlMarmitton.getByXPath("//div[(@class='recipe-card')]");
   		nbrsElementParPage=recipies.size();
   		compteurRecipiesExtract=compteurRecipiesExtract+recipies.size();
   	
   		boolInitialisation=true;
   		
   		 
   		 
    } catch (Exception e) {
        status = 404;
    }
    //protection for memory leak
    webClient.close();
    //----------------------END Initialisation with the first page----------------------------
    
  //------------------------------PARSE ALL RESULT-----------------------------------
    if(boolInitialisation) {
    	 int requestLimit=5;
    	    for(int i=1;i<nbrsElementInt;i++) {
    	    	WebClient wC=webClientCreator();
    	    	if(i>requestLimit) {
    	    		break;
    	    	}
    	    	
    	    	
    	    	 try {
    	    		 String urlMarmittonRecherchePage=urlMarmittonRecherche+start+i*nbrsElementParPage+page+i;
    	       		 HtmlPage htmlMarmitton = (HtmlPage) wC.getPage(urlMarmittonRecherchePage);
    	       		 status = htmlMarmitton.getWebResponse().getStatusCode();
    	       		
    	       		 System.out.println(urlMarmittonRecherchePage);
    	       		List<HtmlElement> recipies = htmlMarmitton.getByXPath("//div[(@class='recipe-card')]");
    	       		compteurRecipiesExtract=compteurRecipiesExtract+recipies.size();
    	       	for (HtmlElement y : recipies){
                    System.out.println("LETEXTE= "+y.asText());
                    System.out.println();
                   }
    	       		
    	       		
    	       		 
    	       		 
    	        } catch (Exception e) {
    	            status = 404;
    	        }
    	    	//protection for memory leak
    	     	wC.close();
    }
   
    	
    	
    	
    	
    	
    	
    }
    
  //------------------------------END PARSE ALL RESULT-----------------------------------
   }
   
   //fonction constuisant l'url de la recherche a partir de la liste d'ingredient
   //---------return null si listIngerdient is empty
   private String URLBuilder(ArrayList<String> ingredientRecette) {
   	String ingredientURLget="aqt=";
   	Boolean IngredientEmpty=false;
   	if(ingredientRecette.isEmpty()==false) {
   		ingredientURLget=ingredientURLget+ingredientRecette.get(0);
   		
   	}else {
   		IngredientEmpty=true;
   	}
   	for(int i=1;i<ingredientRecette.size();i++) {
   		ingredientURLget=ingredientURLget+"-"+ingredientRecette.get(i);
   	}
   //	String typeRecette="type=all";//Cela sera a preciser , ici on a la valeur par default 
   	if(IngredientEmpty) {
   		return null;
   	}else {
   		return URLMarimitton+"?"+ingredientURLget;
   	}
   	
   	
   }
    private WebClient webClientCreator() {
   	 WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(true);
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setRedirectEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setPopupBlockerEnabled(true);
        webClient.getOptions().setTimeout(5000);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);
        return webClient;
   }
}