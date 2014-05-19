import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DkCons {
   public static Scanner dkscannerinput = new Scanner ( System.in ); 
   public static List < String > urllist;
   public static List < String > wordlist;
   public static HashMap < String , String > loadeddata;
   public static HashMap < String , HashMap < String , Integer > > urlmap;
   public static HashMap < String , Integer > wordmap;
   public static HashMap < String , Integer > charcount;
   public static String tagdata;
   public static String notagdata;
   public static boolean timer = false;
   public static long timerStart;
   public static long timerStop;
 
   //URL INPUT METHOD
   public static void urlinput () {
      System.out.println ( "Where to search?" );  
      dkscannerinput = new Scanner ( System.in );
      String urlflow = new String ( dkscannerinput.nextLine() ); 
      urllist = Arrays.asList ( urlflow.split ( ", *" ) ); 
      for ( String url : urllist ) {
         if ( !url.startsWith ( "http://" ) ) {
            urllist.set ( urllist.indexOf ( url ) , "http://" + url );
         }
      }
      System.out.println ( urllist );
   }
   // SEARCH PARAMETERS INPUT METHOD
   public static void wordinput () {       
      System.out.println ( "What to search?" );  
      dkscannerinput = new Scanner ( System.in );
      String inputflow = new String ( dkscannerinput.nextLine() );  // search words in one row
      wordlist = Arrays.asList ( inputflow.split ( ", *" ) );
      System.out.println ( wordlist );
   }
   // WEBSITE CONTENT DOWNLOADING METHOD
   public static void website_content () throws IOException {  
      if ( urllist != null ) {
         loadeddata = new HashMap < String , String > ();
         for ( String url : urllist ) {
            String notagdata = new Scanner ( new URL ( url ).openStream() , "UTF-8" ).useDelimiter ( "\\A" ).next().replaceAll ( "<[^>]*>" , "" );
            loadeddata.put ( url , notagdata );
         }
      }
   }
   // COUNTING OF CHARACTERS METHOD
   public static HashMap < String , Integer > charcounter () {
      HashMap < String , Integer > charcount = new HashMap < String , Integer > ();
      if ( urllist != null ) {
         for ( String url : urllist ) {
            charcount.put ( "Char # at " + url + " is " , loadeddata.get ( url ).length() );
         }    
      } return charcount;
   }
   public static void charcountAnnouncer () {
      if ( urllist != null ) {
         for ( String url : urllist ){
            System.out.println ( "characters at each website " + url + " " + charcount.get ( url ) );
         }
      } else { System.out.println ( "N.B.! No URL provided" ); }
   }
   // MAIN SEARCH METHOD
   public static HashMap < String , HashMap < String , Integer > > search () {
      HashMap < String , HashMap < String, Integer > > urlmap = new HashMap < String , HashMap < String, Integer > > ();                
      if ( loadeddata != null && wordlist != null ) {
         for ( String url : urllist ) {
            HashMap < String, Integer > wordmap = new HashMap < String, Integer > ();
            for ( String word : wordlist ) {
               wordmap.put ( word, 0 );
               for ( int i = 0; i < loadeddata.get ( url ).length() - word.length(); i++ ) {
                  if ( loadeddata.get ( url ).startsWith ( word , i ) ) {
                     wordmap.put ( word , wordmap.get ( word ) + 1 );
                     urlmap.put ( url , wordmap );
                  }
               }
            }
         }
      } return urlmap; 
   }
   public static void searchAnnouncer () {
      if ( loadeddata != null && wordlist != null ){
          for ( String url : urllist ) {
             System.out.println ( "Address " + url + " search results: " );
             for ( String word : wordlist )
                System.out.println ( word + " appeared " + urlmap.get ( url ).get ( word ) + " times" );
          }
          if ( timer == true )
             System.out.println ( "processing took " + ( timerStop - timerStart ) + " milliseconds " );
      } else { System.out.println ( "N.B.! No URL or search parameters provided" ); }
   }
   
   public static void main ( String [] args ) throws IOException {
      System.out.println ( "Hello" ) ;
      for ( ; ; ) {
         String command = dkscannerinput.nextLine();
         timerStart = System.currentTimeMillis();
         DkCons.website_content();
         urlmap = new HashMap < String , HashMap < String, Integer > > ( DkCons.search() );
         charcount = new HashMap < String, Integer > ( DkCons.charcounter() );
         timerStop = System.currentTimeMillis();
         
         if ( command.equals ( "timer" ) ) {
            if ( timer == true ) timer = false ; else timer = true; 
            System.out.println ( "timer ON " + timer );
         } 
         if ( command.equals ( "word" ) )  DkCons.wordinput();
         if ( command.equals ( "url" ) ) DkCons.urlinput();
         if ( command.equals ( "#char" ) ) DkCons.charcountAnnouncer();
         if ( command.equals ( "#word" ) ) DkCons.searchAnnouncer();
         
      }
   }
}
