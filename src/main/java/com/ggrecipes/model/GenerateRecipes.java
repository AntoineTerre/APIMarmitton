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
    public String personnes;
    public String difficulté;
    public String cout;
    public int status = 200;
    public Map<String,String> ingredients = new HashMap<>();

    public void Recipe(String url) throws IOException {

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

        try {
            HtmlPage page = (HtmlPage) webClient.getPage(url);
            status = page.getWebResponse().getStatusCode();
            List<HtmlElement> ingredientsHtml = page.getByXPath("//span[(@class='ingredient')]");
            List<HtmlElement> quantitéHtml = page.getByXPath("//span[contains(@class, 'recipe-ingredient-qt')]");
            for (int i=0;i<ingredientsHtml.size();i++) {
                ingredients.put(ingredientsHtml.get(i).asText(),quantitéHtml.get(i).asText());
            }
            List<HtmlElement> stepsHtml = page.getByXPath("//li[contains(@class, 'recipe-preparation__list')]");
            for (HtmlElement i : stepsHtml) {
                steps.add(i.asText());
            }
            
            List<HtmlElement> tempsHtml = page.getByXPath("//span[contains(@class, 'title-2 recipe-infos__total-time__value')]");
            for (HtmlElement i : tempsHtml) {
                temps = i.asText();
            }
            List<HtmlElement> personnesHtml = page.getByXPath("//span[contains(@class, 'title-2 recipe-infos__quantity__value')]");
            for (HtmlElement i : personnesHtml) {
                personnes = i.asText();
            }
            List<HtmlElement> difficultéHtml = page.getByXPath("//span[(@class= 'recipe-infos__level')]");
            for (HtmlElement i : difficultéHtml) {
                difficulté = i.asText();
            } 
            List<HtmlElement> coutHtml = page.getByXPath("//span[(@class='recipe-infos__budget')]");
            for (HtmlElement i : coutHtml) {
                cout = i.asText();
            }  
            List<HtmlElement> titleHtml = page.getByXPath("//h1[(@class='main-title ')]");
            for (HtmlElement i : titleHtml) {
                title= i.asText();
            } 

        } catch (Exception e) {
            status = 404;
        }
    }

    public GenerateRecipes(Map<String,String> ingredients, ArrayList<String> steps, String title, String temps,String personnes,String difficulté,String cout) {
        this.ingredients = ingredients;
        this.steps = steps;
        this.title = title;
        this.temps = temps;
        this.personnes = personnes;
        this.difficulté = difficulté;
        this.cout = cout;
    }

    public GenerateRecipes() {

    }

}