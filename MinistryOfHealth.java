import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to perform actions related to the distributions of
 * vaccines and displaying all the data from recipients and vaccination centers.
 */
public class MinistryOfHealth {
    private static JsonHelper jsonHelper;
    private ArrayList<Recipient> recipients;
    private ArrayList<VaccinationCenter> vaccinationCenters;

    /**
     * generate a ministry of health to monitor the vaccination centers and
     * recipients
     */
    public MinistryOfHealth() throws IOException {
        MinistryOfHealth.jsonHelper = new JsonHelper();
        loadData();
    }

    private void loadData() throws IOException {
        this.recipients = jsonHelper.readJsonFileToArrayList(JsonHelper.RECIPIENT_JSON_FILE, Recipient.class);
        this.recipients.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        this.vaccinationCenters = jsonHelper.readJsonFileToArrayList(JsonHelper.VC_JSON_FILE, VaccinationCenter.class);
        this.vaccinationCenters.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
    }

    /**
     * get the list of recipients
     *
     * @return the list of recipients who register to the system
     */
    public ArrayList<Recipient> getRecipients() {
        return recipients;
    }

    /**
     * get the list of vaccination centers
     *
     * @return the list of vaccination centers
     */
    public ArrayList<VaccinationCenter> getVaccinationCenters() {
        return vaccinationCenters;
    }

    /**
     * distribute vaccines to different vaccination centers according to the
     * vaccination center capacity or more
     *
     * @param numberOfDoses number of new doses to distribute to different
     *                      vaccination centers
     */

    public void distributeVaccines(int numberOfDoses) throws IOException {
        for (VaccinationCenter vaccinationCenter : vaccinationCenters) {

            if (numberOfDoses > 0) {
                if (numberOfDoses < vaccinationCenter.getCapacityPerDay()) {
                    vaccinationCenter.increaseVaccine(numberOfDoses);
                    break;
                } else {

                    vaccinationCenter.increaseVaccine(numberOfDoses - vaccinationCenter.getCapacityPerDay());

                }

            }
        }
        saveData();
    }

    /**
     * distribute recipients to different vaccination centers according to the
     * vaccination center capacity
     */
    public void distributeRecipients() throws IOException {
        int currentVCIndex = 0;
        int currentVCCapacity = vaccinationCenters.get(currentVCIndex).getCapacityPerDay();
        for (Recipient r : recipients) {
            if (currentVCCapacity == 0) {
                currentVCIndex++;
                // reset to first vc
                if (currentVCIndex >= vaccinationCenters.size()) {
                    currentVCIndex = 0;
                }
                currentVCCapacity = vaccinationCenters.get(currentVCIndex).getCapacityPerDay();
            }
            Appointment appointment = new Appointment(vaccinationCenters.get(currentVCIndex).getName(), null);

            // distribute VC to recipients who
            // 1. do not receive any dose
            if (r.getVaccinationStatus() == 1) {
                // change to next vc
                r.addNewAppointment(appointment);
                r.increaseVaccinationStatus();
            }
            currentVCCapacity--;
        }
        saveData();
    }

    private void saveData() throws IOException {
        jsonHelper.writeJsonFile(this.vaccinationCenters, JsonHelper.VC_JSON_FILE);
        jsonHelper.writeJsonFile(this.recipients, JsonHelper.RECIPIENT_JSON_FILE);
    }
}
