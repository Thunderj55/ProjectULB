package AssigneeStageStudent;

public class Preference {

	int id;
    String anacad;
    String matricule;
    int hopital;
    int service;
    int ordre;
    int typepref;

    public Preference(int id, String anacad, String matricule, int hopital, int service, int ordre, int typepref) {
        this.id = id;
        this.anacad = anacad;
        this.matricule = matricule;
        this.hopital = hopital;
        this.service = service;
        this.ordre = ordre;
        this.typepref = typepref;
    }

}
