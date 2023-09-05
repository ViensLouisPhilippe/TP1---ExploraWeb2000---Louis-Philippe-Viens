package viens.explora;

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
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
}
