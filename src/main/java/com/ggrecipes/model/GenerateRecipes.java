package com.ggrecipes.model;

import java.util.List; // import just the List interface
import java.util.ArrayList; // import just the ArrayList class

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
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
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.io.*;

import static org.junit.Assert.assertNotNull;

public class GenerateRecipes {
    private WebClient webClient;

    public static String Recipe(String url) throws IOException {
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(true);
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setRedirectEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setPopupBlockerEnabled(true);
        webClient.getOptions().setTimeout(5000);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);

        HtmlPage page = (HtmlPage) webClient.getPage(url);
        final HtmlDivision div = page.getHtmlElementById("sticky-desktop-only");
        String textSource = div.toString();

        List<HtmlAnchor> ingredients = page.getByXPath("//a[@class='recipe-ingredients__list']");

        String title = page.getTitleText();

        // System.out.println("Le titre est le suivant : " + title);
        // System.out.println("La page est la suivante : " + page);
        // Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
        // final String pageAsXml = page.asXml();
        // System.out.println("La pageXml est la suivante : " +pageAsXml);
        // Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));
        // on s'interesse a cette partie
        final String pageAsText = page.asText();
        // Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS
        // protocols"));
        List<Integer> intList = new ArrayList<Integer>();
        String ingrd = pageAsText;
        int indexIngrd = pageAsText.indexOf("Ingrédients");
        int indexUst = pageAsText.indexOf("ustensiles");
        // System.out.println(indexIngrd + " "+ indexUst);
        ingrd = ingrd.substring(indexIngrd, indexUst);
        System.out.println(ingrd);
        return ingrd;
    }
}