package AssigneeStageStudent;
import java.util.List;
import java.util.Map;

public class Process {

	public static void main(String[] args) {
		
        List<Classement> classements = ApiClient.getClassements();
        List<Place> places = ApiClient.getPlaces();
        List<Preference> preferences = ApiClient.getPreferences();

        // Convertir les classements en Etudiant
        List<Etudiant> etudiants = ApiClient.convertirClassementsEnEtudiants(classements, preferences);

        // Utiliser les données récupérées pour attribuer les stages
        Map<String, String> affectations = AttributionStage.attribuerStages(etudiants, places);

        // Soumettre les résultats à l'API
        ApiClient.soumettreResultats(affectations);
    }
}
