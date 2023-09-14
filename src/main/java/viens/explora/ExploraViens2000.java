package viens.explora;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.awt.image.ImageWatched;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Struct;

public class ExploraViens2000 {

    public static void main(String[] args) {
        //ÉTAPE 1 TODO 1: Arguments

        // Argument 1/4 On test s'il y a 2 arguments
        if (args.length != 2) {
            System.out.println("Merci de nous fournir 2 arguments :\n" +
                    "l'URL de la page de départ, la liste de mots clés à ignorer séparés par des espaces entre guillemets.\n" +
                    "Par exemple : https://info.cegepmontpetit.ca/3N5-Prog3/z/index.html \"pipo popi\"\n" +
                    "\n");
        }
        //Argument 2/4 on test si l'URL est bien formée
        if (!isURLTextValid(args[0]))
            System.out.println("Nous avons rencontré un problème avec l'URL fournie : " + args[0] + "\nMerci de fournir une URL bien formée");

        //Argument 3/4 on confirme si l'URL est valid
        if (!isURlValid(args[0]))
            System.out.println("Il n'y a pas de page correspondante à l'URL : " + args[0] + "\nMerci de fournir une URL correspondant à une page");

        //Argument 4/4 On valide que la liste de mots clés ne contient pas de répétitions.
        if (!isListValid(args[1])) {
            System.out.println("Mots clés fournis incorrects " + args[1]);
            System.out.println("Merci de ne pas avoir de répétitions dans les mots clés : ");
        }

        //ÉTAPE 2 TODO "Exploration"
        System.out.println("Les arguments sont corrects, nous commençons l'exploration de " + args[0]);
        //Exploration 1 : afficher le titre et l'url complet
        try {
            String url = args[0];
            Document doc = Jsoup.connect(url).get();
            String titre = doc.title();
            Elements links = doc.select("a");
            int nbrLiens = links.size();
            System.out.println("Titre : " + titre + "          URL : " + args[0] + " Liens " + nbrLiens);

            ExplorationDeLien(args[0]);



        } catch (Exception e) {
            System.out.println("Erreur");
        }


    }

    //Début Region TODO 1
    public static boolean isURLTextValid(String url) {
        //on test si l'URL est bien formée
        try {
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
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isListValid(String list) {

        //On vérifie qu'il n'y est pas de répétitions de mots dans la liste de mots
        try {
            String[] Tab = list.split(" ");

            return isTabValid(Tab);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isTabValid(String[] tab) {
        for (int a = 0; a < tab.length; a++) {
            for (int b = 0; b < tab.length; b++) {
                if (tab[a].equals(tab[b]) && a != b) {
                    return false;
                }
            }
        }
        return true;
    }
    //Fin Region TODO 1

    //Début Region TODO 2
    public static void  ExplorationDeLien(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a");

                if (!links.isEmpty()) {
                    while (isURLTextValid(links.first().attr("abs:href")))
                     {
                        // Extraire le premier lien trouvé
                        Element firstLink = links.get(0);

                        // Récupérer l'URL du premier lien
                        String firstLinkUrl = firstLink.attr("abs:href");
                        if (isURLTextValid(firstLinkUrl)) {
                            // Afficher l'URL du premier lien
                            Document docFirstLink = Jsoup.connect(firstLinkUrl).get();
                            System.out.println("Titre : " + docFirstLink.title() + "          URL : " + firstLinkUrl);
                            ExplorationDeLien(firstLinkUrl);
                        } else
                        {
                            System.out.println("URL ignorée : " + firstLinkUrl);
                        }
                    }
                    System.out.println("URL ignorée : " + links.first().attr("abs:href"));
                    // Extraire le premier lien trouvé
                    Element NextLink = links.get(1);

                    // Récupérer l'URL du premier lien
                    String NextLinkUrl = NextLink.attr("abs:href");
                    if (isURLTextValid(NextLinkUrl)) {
                        // Afficher l'URL du premier lien
                        Document docNextLink = Jsoup.connect(NextLinkUrl).get();
                        System.out.println("Titre : " + docNextLink.title() + "          URL : " + NextLinkUrl);
                        ExplorationDeLien(NextLinkUrl);
                    } else
                    {
                        System.out.println("URL ignorée : " + NextLinkUrl);
                    }

                } else {
                    System.out.println("L'exploration s'est arrêtée, la page " + url + " ne contient aucun lien valide.");

                }


        } catch (Exception e) {

        }

    }

    
    //Fin Region TODO 2
}
