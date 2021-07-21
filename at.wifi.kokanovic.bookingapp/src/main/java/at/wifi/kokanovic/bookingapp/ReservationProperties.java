package at.wifi.kokanovic.bookingapp;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReservationProperties {

	StringProperty reservationIdProp = new SimpleStringProperty();
	StringProperty resNameProp = new SimpleStringProperty();
	StringProperty checkInProp = new SimpleStringProperty();
	StringProperty checkOutProp = new SimpleStringProperty();
	StringProperty roomNrProp = new SimpleStringProperty();
	StringProperty guestsNameProp = new SimpleStringProperty();

	public String getReservationIdProp() {
		return reservationIdProp.getValue();

	}

	public StringProperty reservationIdProp() {
		return reservationIdProp;

	}

	public void setReservationIdProp(StringProperty reservationIdProp) {
		this.reservationIdProp = reservationIdProp;
	}

	public String getResNameProp() {
		return resNameProp.getValue();

	}

	public StringProperty resNameProp() {
		return resNameProp;

	}

	public void setResNameProp(StringProperty resNameProp) {
		this.resNameProp = resNameProp;
	}

	public String getCheckInProp() {
		return checkInProp.getValue();
	}

	public StringProperty checkInProp() {
		return checkInProp;

	}

	public void setCheckInProp(StringProperty checkInProp) {
		this.checkInProp = checkInProp;

	}

	public String getCheckOutProp() {
		return checkOutProp.getValue();
	}

	public StringProperty checkOutProp() {
		return checkOutProp;

	}

	public void setCheckOutProp(StringProperty checkOutProp) {
		this.checkOutProp = checkOutProp;

	}

	public String getRoomNrProp() {
		return roomNrProp.getValue();
	}

	public StringProperty roomNrProp() {
		return roomNrProp;

	}

	public void setRoomNrProp(StringProperty roomNrProp) {
		this.roomNrProp = roomNrProp;

	}

	public String getGuestsNameProp() {
		return guestsNameProp.getValue();
	}

	public StringProperty guestsNameProp() {

		return guestsNameProp;
	}

	public void setGuestsNameProp(StringProperty guestsNameProp) {
		this.guestsNameProp = guestsNameProp;
	}
}
