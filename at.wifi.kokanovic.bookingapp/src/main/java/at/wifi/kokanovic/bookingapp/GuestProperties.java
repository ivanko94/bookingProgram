package at.wifi.kokanovic.bookingapp;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GuestProperties {

	StringProperty idNumberProp = new SimpleStringProperty();
	StringProperty firstNameProp = new SimpleStringProperty();
	StringProperty lastNameProp = new SimpleStringProperty();
	StringProperty dateOfBirthProp = new SimpleStringProperty();
	StringProperty genderProp = new SimpleStringProperty();
	StringProperty idCardNumberProp = new SimpleStringProperty();
	StringProperty streetProp = new SimpleStringProperty();
	StringProperty cityProp = new SimpleStringProperty();
	StringProperty zipProp = new SimpleStringProperty();
	StringProperty countryProp = new SimpleStringProperty();
	StringProperty nationalityProp = new SimpleStringProperty();

	public String getIdNumberProp() {
		return idNumberProp.getValue();
	}

	public StringProperty idNumberProp() {
		return idNumberProp;
	}

	public void setIdNumberProp(StringProperty idNumberProp) {
		this.idNumberProp = idNumberProp;
	}

	public StringProperty firstNameProp() {
		return firstNameProp;
	}

	public String getFirstNameProp() {
		return firstNameProp.getValue();

	}

	public void setFirstNameProp(StringProperty firstNameProp) {
		this.firstNameProp = firstNameProp;
	}

	public StringProperty lastNameProp() {
		return lastNameProp;
	}

	public String getLastNameProp() {
		return lastNameProp.getValue();
	}

	public void setLastNameProp(StringProperty lastNameProp) {
		this.lastNameProp = lastNameProp;
	}

	public StringProperty dateOfBirthProp() {
		return dateOfBirthProp;
	}

	public String getDateOfBirthProp() {
		return dateOfBirthProp.getValue();
	}

	public void setDateOfBirthProp(StringProperty dateOfBirthProp) {
		this.dateOfBirthProp = dateOfBirthProp;
	}

	public StringProperty genderProp() {
		return genderProp;
	}

	public String getGenderProp() {
		return genderProp.getValue();
	}

	public void setGenderProp(StringProperty genderProp) {
		this.genderProp = genderProp;
	}

	public StringProperty idCardNumberProp() {
		return idCardNumberProp;
	}

	public String getIdCardNumberProp() {
		return idCardNumberProp.getValue();
	}

	public void setIdCardNumberProp(StringProperty idCardNumberProp) {
		this.idCardNumberProp = idCardNumberProp;
	}

	public StringProperty streetProp() {
		return streetProp;
	}
	public String getStreetProp() {
		return streetProp.getValue();
	}
	public void setStreetProp(StringProperty streetProp) {
		this.streetProp = streetProp;
	}

	public StringProperty cityProp() {
		return cityProp;
	}
	public String getCityProp() {
		return cityProp.getValue();
	}
	public void setCityProp(StringProperty cityProp) {
		this.cityProp = cityProp;
	}

	public StringProperty zipProp() {
		return zipProp;
	}
	public String getZipProp() {
		return zipProp.getValue();
	}
	public void setZipProp(StringProperty zipProp) {
		this.zipProp = zipProp;
	}

	public StringProperty countryProp() {
		return countryProp;
	}
	public String getCountryProp() {
		return countryProp.getValue();
	}
	public void setCountryProp(StringProperty countryProp) {
		this.countryProp = countryProp;
	}

	public StringProperty nationalityProp() {
		return nationalityProp;
	}
	public String getNationalityProp() {
		return nationalityProp.getValue();
	}
	public void setNationalityProp(StringProperty nationalityProp) {
		this.nationalityProp = nationalityProp;
	}
}
