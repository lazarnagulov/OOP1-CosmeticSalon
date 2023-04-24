package com.nagulov.treatments;

public enum TreatmentStatus {
	SCHEDULED("Scheduled"),
	PERFORMED("Performed"),
	CANCELED_BY_THE_CLIENT("Canceled by the client"),
	CANCELED_BY_THE_SALON("Canceled by the salon"),
	DID_NOT_SHOW_UP("Did not show up");

	private final String status;

	private TreatmentStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
