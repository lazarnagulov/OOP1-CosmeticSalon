package com.nagulov.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;

import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;

public interface ReceptionistControl {
	Treatment createTreatment(TreatmentStatus status, CosmeticService service, String treatment, Beautician beautician, LocalDateTime date, Client client);
	void removeTreatment(Treatment treatment);
	void removeTreatment(int id);
	void updateTreatment(Treatment treatment, HashMap<String, String> updateMap);
	Treatment getTreatment(int id);
}
