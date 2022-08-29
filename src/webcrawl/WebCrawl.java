/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package webcrawl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author acer
 */
public class WebCrawl {

    /**
     * @param args the command line arguments
     */
    public static Queue<String> queue=new LinkedList<>();
    public static Set<String> marked= new HashSet<>();
    public static String regex="http[s]*://(\\w+\\.)*(\\w+)";
    public static void BFSAlgorithm(String root) throws IOException{
        queue.add(root);
        while(!queue.isEmpty()){
            String crawleURL=queue.poll();
            System.out.println("\n=== Site crealed:"+crawleURL+"===");
            //we linmit to 100 website here
            if(marked.size()>100)
                return;
            boolean ok=false;
            URL url=null;
            BufferedReader br=null;
            while(!ok){
                try{
                    url=new URL(crawleURL);
                    br= new BufferedReader(new InputStreamReader(url.openStream()));
                    ok=true;
                }catch(MalformedURLException e){
                        System.out.println("Maformed URL:"+crawleURL);
                }catch(IOException ioe){
                    System.out.println("IOException for URL:"+crawleURL);
                    crawleURL=queue.poll();
                    ok=false;
                }
            }
            StringBuilder s = new StringBuilder();
            //String tmp= null;
            while((crawleURL=br.readLine())!=null){
                s.append(crawleURL);
            }
            crawleURL=s.toString();
            Pattern pattern=Pattern.compile(regex);
            Matcher matcher= pattern.matcher(crawleURL);
            
            while(matcher.find()){
                String m= matcher.group();
                
                if(!marked.contains(m)){
                    marked.add(m);
                    System.out.println("Sited added for crawling:"+m);
                    queue.add(m);
                }
            }
            
            
        }
    }
    public static void ShowResult(){
        System.out.println("\n\n Result:");
        System.out.println("Web sites crawled:"+marked.size()+"\n");
        for(String s :marked){
            System.out.println("*"+s);
        }
    }
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            //BFSAlgorithm("https://www.section.io/engineering-education/springboot-antmatchers/");
            BFSAlgorithm("https://www.javatpoint.com/digital-electronics");
            
            ShowResult();
        } catch (IOException ex) {
            Logger.getLogger(WebCrawl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
