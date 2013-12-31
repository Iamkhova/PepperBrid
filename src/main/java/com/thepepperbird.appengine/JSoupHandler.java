package com.thepepperbird.appengine;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.logging.Logger; 
import java.util.Collection;
import java.util.Iterator;


import java.io.IOException;

public class JSoupHandler
{

public static final Logger log = Logger.getLogger(JSoupHandler.class.getName());

   /*
    * This reads the RSS link and pulls additonal content from the website.
    * getContent(String _rss)
    */
   public String getContent(String _rss) throws IOException
   {
   
     log.info("Pulling Expanded Content.");
     Document doc = Jsoup.connect(_rss).get();
     
     String theValue = "";
     String linkTag= "";

     // Pulls all the paragraphs from the content and put it in a string
     // This area may need to get smarter based on the feed types.
     Elements paragraphs = doc.select("p");
     for(Element p : paragraphs)
        theValue = theValue + p.text();
     
    //TODO Remove Footer Junk    
    //String tmpString = theValue.replace("Copyright ? 2013 United Nations Mission in Liberia. All rights reserved. Distributed by AllAfrica Global Media (allAfrica.com). To contact the copyright holder directly for corrections ? or for permission to republish or make other authorized use of this material, click here.AllAfrica aggregates and indexes content from over 130 African news organizations, plus more than 200 other sources, who are responsible for their own reporting and views. Articles and commentaries that identify allAfrica.com as the publisher are produced or commissioned by AllAfrica.AllAfrica is a voice of, by and about Africa - aggregating, producing and distributing 2000 news and information items daily from over 130 African news organizations and our own reporters to an African and global public. We operate from Cape Town, Dakar, Lagos, Monrovia, Nairobi and Washington DC.? 2013 AllAfrica // Privacy // Contact","");      
        
     theValue = limit(theValue, 700);
     log.info("Finished Pulling Expanded Content.");
     
     //TODO This is the link tag. Will need to eventually pull this from the DB.
     linkTag="<br><br><a href=\"" + _rss + "\" target=\"_blank\">Read More</a>";
     
     //Merge Content
     theValue = "<br>" + theValue + linkTag;
     log.info("New Store" + theValue);
     
     return theValue;
   }
   
 
    /*
     * Formats the RSS string to my liking.. 
     * 
     */ 
    public String formatRSS(String _rss)
    {
      String tmpString = _rss.replace("https://www.google.com/url?q=",""); // Remove Google Header
      String word = "&ct";

     //Rushed through.. remove google suffix code
     for (int i = -1; (i = tmpString.indexOf(word, i + 1)) != -1; ) {
        StringBuilder buf = new StringBuilder(tmpString);
        buf.setLength(i);
        tmpString = buf.toString(); }
         log.info("rss" + tmpString);
      return tmpString;
    }
   
   /*
    * Truncates the string
    *  private static String limit(String value, int length)
    */
   private static String limit(String value, int length)
   {
    StringBuilder buf = new StringBuilder(value);
    if (buf.length() > length)
    {
      buf.setLength(length);
      buf.append("...");
    }

    return buf.toString();
  }
  
 
 


}