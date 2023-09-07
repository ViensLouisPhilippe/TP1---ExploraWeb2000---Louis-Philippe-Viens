package viens.explora;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.MalformedURLException;
import java.net.URL;

public class ExploraViens2000 {

    public static void main(String[] args) {
        //On test s'il y a 2 arguments
        if (args.length != 2){
            System.out.println("Merci de nous fournir 2 arguments :\n" +
                    "l'URL de la page de départ, la liste de mots clés à ignorer séparés par des espaces entre guillemets.\n" +
                    "Par exemple : https://info.cegepmontpetit.ca/3N5-Prog3/z/index.html \"pipo popi\"\n" +
                    "\n");
        }
        //on test si l'URL est bien formée
        if(!isURLTextValid(args[0]))
            System.out.println("Nous avons rencontré un problème avec l'URL fournie : " + args[0] + "\nMerci de fournir une URL bien formée");

        //on confirme si l'URL est valid
        if(!isURlValid(args[0]))
            System.out.println("Il n'y a pas de page correspondante à l'URL : " + args[0] + "\nMerci de fournir une URL correspondant à une page");

        //On valide que la liste de mots clés ne contient pas de répétitions.
        if (!isListValid(args[1]))
        {
            System.out.println("Mots clés fournis incorrects " + args[1]);
            System.out.println("Merci de ne pas avoir de répétitions dans les mots clés : ");
        }

    }
    public static boolean isURLTextValid(String url){
        //on test si l'URL est bien formée
        try{
            URL site = new URL(url);

        } catch (MalformedURLException e) {

            return false;
        }
        return true;
    }
    public static boolean isURlValid(String url) {


        /* Try creating a valid URL */
        try {
            Document doc = Jsoup.connect(url).get();
            return true;
        }

        catch (Exception e) {
            return false;
        }
    }
    public  static boolean isListValid(String list){
        try {
            String[] Tab = list.split(" ");

            return isTabValid(Tab);
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public  static boolean isTabValid(String[] tab)
    {
        for (int i = 0; i<tab.length; i++)
        {
            for (int a = tab.length; a>=0; a--) {
                if (tab[i].contains(tab[a]) && i != a)
                    return false;

            }

        }
        return true;
    }

}
