package com.ggrecipes.model;

import org.junit.Assert;
import static org.junit.Assert.*;				
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class GenerateRecipes {
    public static void main(String[] args) {
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage("https://www.marmiton.org/recettes/recette_harcha-galette-de-semoule-fine_24290.aspx");
            String title = page.getTitleText();
            System.out.println("Le titre est le suivant : " + title);
            //System.out.println("La page est la suivante : " + page);
            //Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
            
            final String pageAsXml = page.asXml();
            //System.out.println("La pageXml est la suivante : " +pageAsXml);
            //Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));
            // on s'interesse a cette partie 
            final String pageAsText = page.asText();
            System.out.println("La pageAsText est la suivante : " +pageAsText);
            //Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
        }catch (Exception e) {
        e.printStackTrace();
    }
    }
}