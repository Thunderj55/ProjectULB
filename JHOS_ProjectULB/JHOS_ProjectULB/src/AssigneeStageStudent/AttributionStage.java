package AssigneeStageStudent;
import java.util.*;

public class AttributionStage {
	public static Map<String, String> attribuerStages(List<Etudiant> etudiants, List<Place> places) {
        Map<String, String> affectations = new HashMap<>();
        Map<String, Integer> placesRestantes = new HashMap<>();

        for (Place place : places) {
            placesRestantes.put(place.hopital + "-" + place.service, place.places);
        }

        etudiants.sort(Comparator.comparingInt(e -> e.rang));

        for (Etudiant etudiant : etudiants) {
            for (String preference : etudiant.preferences) {
                if (placesRestantes.getOrDefault(preference, 0) > 0) {
                    affectations.put(etudiant.matricule, preference);
                    placesRestantes.put(preference, placesRestantes.get(preference) - 1);
                    break;
                }
            }
        }

        return affectations;
    }


}
