package AssigneeStageStudent;
import java.util.*;

public class Etudiant {

	String matricule;
    int rang;
    List<String> preferences;

    public Etudiant(String matricule, int rang, List<String> preferences) {
        this.matricule = matricule;
        this.rang = rang;
        this.preferences = preferences;
    }

}
