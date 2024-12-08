package AssigneeStageStudent;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.*;

public class ApiClient {

	private static final String API_URL = "https://codeval.polesante.ulb.be";
    private static final String IDENTIFIANT = "ApW8vH$M-&D:^Gy]n`Q\"7uX.KUtj3a~V!,S4PeJc>*+BgmwZF6";
    private static final Gson gson = new Gson();


    //Get Data from API
    public static List<Classement> getClassements() {
        String endpoint = "/classements";
        String response = sendGetRequest(endpoint);
        return parseClassements(response);
    }

    public static List<Hopital> getHopitaux() {
        String endpoint = "/hopitaux";
        String response = sendGetRequest(endpoint);
        return parseHopitaux(response);
    }

    public static List<Service> getServices() {
        String endpoint = "/services";
        String response = sendGetRequest(endpoint);
        return parseServices(response);
    }

    public static List<Place> getPlaces() {
        String endpoint = "/places";
        String response = sendGetRequest(endpoint);
        return parsePlaces(response);
    }

    public static List<Preference> getPreferences() {
        String endpoint = "/preferences";
        String response = sendGetRequest(endpoint);
        return parsePreferences(response);
    }

    // Send get request to the API
    private static String sendGetRequest(String endpoint) {
        try {
            URL url = new URL(API_URL + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + IDENTIFIANT);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Parse data
    private static List<Classement> parseClassements(String jsonResponse) {
        Type listType = new TypeToken<List<Classement>>() {}.getType();
        return gson.fromJson(jsonResponse, listType);
    }

    private static List<Hopital> parseHopitaux(String jsonResponse) {
        Type listType = new TypeToken<List<Hopital>>() {}.getType();
        return gson.fromJson(jsonResponse, listType);
    }

    private static List<Service> parseServices(String jsonResponse) {
        Type listType = new TypeToken<List<Service>>() {}.getType();
        return gson.fromJson(jsonResponse, listType);
    }

    private static List<Place> parsePlaces(String jsonResponse) {
        Type listType = new TypeToken<List<Place>>() {}.getType();
        return gson.fromJson(jsonResponse, listType);
    }

    private static List<Preference> parsePreferences(String jsonResponse) {
        Type listType = new TypeToken<List<Preference>>() {}.getType();
        return gson.fromJson(jsonResponse, listType);
    }

    //Convert studient to affectation
    public static List<Etudiant> convertirClassementsEnEtudiants(List<Classement> classements, List<Preference> preferences) {
        Map<String, List<Preference>> preferencesMap = preferences.stream()
                .collect(Collectors.groupingBy(p -> p.matricule));

        return classements.stream()
                .map(c -> new Etudiant(c.matricule, c.rang, preferencesMap.getOrDefault(c.matricule, List.of()).stream()
                        .sorted(Comparator.comparingInt(p -> p.ordre))
                        .map(p -> p.hopital + "-" + p.service)
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    
    //Submit result
    public static void soumettreResultats(Map<String, String> affectations) {
        String endpoint = "/resultats";
        try {
            URL url = new URL(API_URL + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + IDENTIFIANT);
            connection.setDoOutput(true);

            String jsonInputString = convertirAffectationsEnJson(affectations);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int code = connection.getResponseCode();
            System.out.println("Response Code: " + code);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertirAffectationsEnJson(Map<String, String> affectations) {
        return Gson.toJson(affectations);
    }

}
