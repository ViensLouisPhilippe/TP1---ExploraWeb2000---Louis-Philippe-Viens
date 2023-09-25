package viens.explora;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExploraViens2000 {
private static ArrayList<String> LISTE;
    public static void ListeUrl() {
        LISTE = new ArrayList<String>();
    }
private static ArrayList<String> LISTEEMAIL;
    public static void ListeEmail() {
        LISTEEMAIL = new ArrayList<String>();
    }

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
        else if (!isURLTextValid(args[0]))
            System.out.println("Nous avons rencontré un problème avec l'URL fournie : " + args[0] + "\nMerci de fournir une URL bien formée");

        //Argument 3/4 on confirme si l'URL est valid
        else if (!isURlValid(args[0]))
            System.out.println("Il n'y a pas de page correspondante à l'URL : " + args[0] + "\nMerci de fournir une URL correspondant à une page");

        //Argument 4/4 On valide que la liste de mots clés ne contient pas de répétitions.
        else if (!isListValid(args[1])) {
            System.out.println("Mots clés fournis incorrects " + args[1]);
            System.out.println("Merci de ne pas avoir de répétitions dans les mots clés : ");
        }

        //ÉTAPE 2 TODO "Exploration"
        else {
            System.out.println("Les arguments sont corrects, nous commençons l'exploration de " + args[0]);
            //Exploration 1 : afficher le titre et l'url complet
            try {
                String url = args[0];
                Document doc = Jsoup.connect(url).get();
                String titre = doc.title();
                Elements links = doc.select("a");
                int nbrLiens = links.size();
                System.out.println("Titre : " + titre + "          URL : " + args[0] + " Liens " + nbrLiens);
                ListeUrl();
                ListeEmail();
                ExplorationDeLien(args[0],args[1]);
                for (String urls : LISTE) {
                    HtmlExplorer(urls);
                }
                CollecteCourriel(LISTEEMAIL);


            } catch (Exception e) {
                System.out.println("Erreur");
            }
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
    public static void  ExplorationDeLien(String url, String motCle) {
        try {
            Document doc = Jsoup.connect(url.replaceAll("#","")).get();
            Elements links = doc.select("a");

            //TODO 7 exploration
            if (!LISTE.contains(url))
            {
                LISTE.add(url);
                if (!links.isEmpty()) {

                    while (isURLTextValid(links.first().attr("abs:href")))
                    {
                        // Extraire le premier lien trouvé
                        Element firstLink = links.first();
                        //Chercher le deuxieme url
                        Element deuxiemeLink = links.get(1);
                        // Récupérer l'URL du premier lien
                        String firstLinkUrl = firstLink.attr("abs:href").replaceAll("#","");
                        if (isURLTextValid(firstLinkUrl) && isURlValid(firstLinkUrl)) {
                            // Afficher l'URL du premier lien
                            Document docFirstLink = Jsoup.connect(firstLinkUrl).get();
                            //Compter le nombre de lien dans le premier url
                            Elements nbrlien = docFirstLink.select("a");
                            //TODO 4 Exploration
                            // Url avec motclé?

                            //on recupère les info du 2ème link
                            String secondLinkUrl = deuxiemeLink.attr("abs:href");
                            Document docSecondLink = Jsoup.connect(secondLinkUrl).get();


                            if (MotCleDansUrl(motCle, firstLinkUrl))
                            {
                                if (isURLTextValid(secondLinkUrl) && isURlValid(secondLinkUrl))
                                {
                                    System.out.println("Titre : " + docSecondLink.title() + "          URL : " + secondLinkUrl + " Liens " + docSecondLink.select("a").size());
                                    ExplorationDeLien(links.get(1).attr("abs:href"), motCle);
                                    break;
                                }
                                else
                                {
                                    ExplorationDeLien(links.get(2).attr("abs:href"), motCle);
                                    System.out.println("URL ignorée : " + secondLinkUrl);
                                    break;
                                }


                            }
                            else
                            {

                                //TODO 5 Exploration
                                if(firstLinkUrl.equals(url))
                                {
                                    if (MotCleDansUrl(motCle, secondLinkUrl))
                                    {
                                        Element troisiemeLink = links.get(2);
                                        //on recupère les info du 3eme link
                                        String thirdLinkUrl = troisiemeLink.attr("abs:href");
                                        //Document docThirdLink = Jsoup.connect(secondLinkUrl).get();
                                        if (isURLTextValid(thirdLinkUrl) && isURlValid(thirdLinkUrl))
                                        {
                                            //System.out.println("Titre : " + docThirdLink.title() + "          URL : " + thirdLinkUrl + " Liens " + docThirdLink.select("a").size());
                                            ExplorationDeLien(links.get(2).attr("abs:href"), motCle);
                                            break;
                                        }
                                        else
                                        {
                                            System.out.println("URL ignorée : " + thirdLinkUrl);
                                            ExplorationDeLien(links.get(3).attr("abs:href"), motCle);
                                            break;
                                        }
                                    }
                                    else
                                    {
                                        if (isURLTextValid(secondLinkUrl) && isURlValid(secondLinkUrl))
                                        {
                                            System.out.println("Titre : " + docSecondLink.title() + "          URL : " + secondLinkUrl + " Liens " + docSecondLink.select("a").size());
                                            ExplorationDeLien(deuxiemeLink.attr("abs:href"), motCle);
                                            break;
                                        }
                                        else
                                        {
                                            ExplorationDeLien(links.get(2).attr("abs:href"), motCle);
                                            System.out.println("URL ignorée : " + secondLinkUrl);
                                            break;
                                        }

                                    }

                                }
                                else{
                                    System.out.println("Titre : " + docFirstLink.title() + "          URL : " + firstLinkUrl + " Liens " + nbrlien.size());
                                    ExplorationDeLien(firstLinkUrl, motCle);
                                    break;
                                }
                            }
                            //Fin TODO 4
                        } else
                        {
                            System.out.println("URL ignorée : " + firstLinkUrl);
                        }

                    }
                }
                else {
                    System.out.println("L'exploration s'est arrêtée, la page " + url + " ne contient aucun lien valide.");

                }
            }

            else{
                System.out.println("L'exploration s'est arrêtée car nous avons rencontré une URL déjà explorée " + url);
            }



        } catch (Exception e) {

        }

    }
    public static boolean MotCleDansUrl(String motCle, String Url) {

        String[] list = motCle.split(" ");
        for (int i = 0; i < list.length; i++) {

            if (Url.contains(list[i]))
                return true;

        }
        return false;
    }
    //Fin Region TODO 2

    //TODO Fichier Explorer
    //Sauvegarde des pages html explorer
    public static void HtmlExplorer(String url)
    {

        try{
            String repertoire = System.getProperty("user.dir") + "/siteWeb";
            File siteWeb = new File(repertoire);
            if (!siteWeb.exists())
            {
                siteWeb.mkdirs();
            }
            URL newUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) newUrl.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String ligne;

            while ((ligne = reader.readLine()) != null) {
                stringBuilder.append(ligne);
                stringBuilder.append("\n");
            }
            //TODO email Recuperer les emails dans le stringbuilder puis les remplacers par louisphilippe.viens@pipo.org
            String adresseDeRemplacement = "louisphilippe.viens@pipo.org";
            String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
            Pattern pattern = Pattern.compile(emailPattern);
            Matcher matcher = pattern.matcher(stringBuilder.toString());
            while (matcher.find()) {
                String email = matcher.group().toLowerCase();
                if (!LISTEEMAIL.contains(email))
                    LISTEEMAIL.add(email);
            }
            String modifiedContent = matcher.replaceAll(adresseDeRemplacement);
            //fin TODO email
            reader.close();
            connection.disconnect();
            String path = newUrl.getPath();
            String[] pathSegments = path.split("/");
            String nomFichier = pathSegments[pathSegments.length - 1];
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(siteWeb + "/" + nomFichier))) {
                writer.write(modifiedContent);
            }
        }
        catch (Exception e)
        {
            System.out.println("erreur");
        }
    }
    public static void CollecteCourriel(List<String> listEmail)
    {
        Collections.sort(listEmail);
        String repertoire = System.getProperty("user.dir") + "/src/resultat";
        File resultat = new File(repertoire);
        if (!resultat.exists())
        {
            resultat.mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultat + "/courriel.txt"))) {
            for (String email : listEmail) {
                System.out.println(email);
                writer.write(email + "\n");
            }
        }
        catch (Exception e)
        {
            System.out.println("Erreur dans la procedure de collecte de courriel");
        }

    }
}
