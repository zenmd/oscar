package org.oscarehr.PMmodule.web.formbean;

import org.apache.struts.validator.ValidatorForm;
import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.Demographic;

import com.ibm.websphere.client.applicationclient.ClientRAR;
import com.quatro.web.intake.OptionList;
import com.quatro.model.LookupCodeValue;
import java.util.List;
import org.oscarehr.PMmodule.model.QuatroIntake;

public class QuatroIntakeEditForm extends ValidatorForm{
    private String clientId;
    private String intakeId;
	
	private String dob;
    private Demographic client;

    private OptionList optionList;
    private List programList;
    private List programTypeList;

    private LookupCodeValue language;
    private LookupCodeValue originalCountry;
    
    private QuatroIntake intake;
    private ClientReferral clientReferral;

	public Demographic getClient() {
		return client;
	}

	public void setClient(Demographic client) {
		this.client = client;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public QuatroIntake getIntake() {
		return intake;
	}

	public void setIntake(QuatroIntake intake) {
		this.intake = intake;
	}

	public String getIntakeId() {
		return intakeId;
	}

	public void setIntakeId(String intakeId) {
		this.intakeId = intakeId;
	}

	public LookupCodeValue getLanguage() {
		return language;
	}

	public void setLanguage(LookupCodeValue language) {
		this.language = language;
	}

	public OptionList getOptionList() {
		return optionList;
	}

	public void setOptionList(OptionList optionList) {
		this.optionList = optionList;
	}

	public LookupCodeValue getOriginalCountry() {
		return originalCountry;
	}

	public void setOriginalCountry(LookupCodeValue originalCountry) {
		this.originalCountry = originalCountry;
	}

	public List getProgramList() {
		return programList;
	}

	public void setProgramList(List programList) {
		this.programList = programList;
	}

	public List getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List programTypeList) {
		this.programTypeList = programTypeList;
	}

	public ClientReferral getClientReferral() {
		return clientReferral;
	}

	public void setClientReferral(ClientReferral clientReferral) {
		this.clientReferral = clientReferral;
	}
    
/*    
    private String referredBy;
    private String contactName;
    private String contactNumber;
    private String contactEmail;

    private LookupTagValue language;
    private String youth;
    private String aboriginal;
    private String aboriginalOther;
    private String VAW;
    private String curSleepArrangement;
    private String inShelterBefore;
    private String lengthOfHomeless;
    private String reasonForHomeless;
    
    private String pregnant;
    private String disclosedAbuse;
    private String disability;
    private String observedAbuse;
    private String disclosedMentalIssue;
    private String poorHygiene;
    private String observedMentalIssue;
    private String disclosedAlcoholAbuse;
    private String observedAlcoholAbuse;

    private String birthCertificate;
    private String birthCertificateYN;
    private String SIN;
    private String SINYN;
    private String healthCardNo;
    private String healthCardNoYN;
    private String driverLicenseNo;
    private String driverLicenseNoYN;
    private String citizenCardNo;
    private String citizenCardNoYN;
    private String nativeReserveNo;
    private String nativeReserveNoYN;
    private String veteranNo;
    private String veteranNoYN;
    private String recordLanding;
    private String recordLandingYN;
    private String libraryCard;
    private String libraryCardYN;
    private String idOther;

    private String sourceIncome;
    private String income;
    private String incomeWorkerName1;
    private String incomeWorkerPhone1;
    private String incomeWorkerEmail1;
    private String incomeWorkerName2;
    private String incomeWorkerPhone2;
    private String incomeWorkerEmail2;
    private String incomeWorkerName3;
    private String incomeWorkerPhone3;
    private String incomeWorkerEmail3;
    
    private String livedBefore;
    private String livedBeforeOther;
    private String statusInCanada;
    private LookupTagValue originalCountry;
    private String referredTo;
    private String reasonNoAdmit;

    private String program;
    private String comments;

    public String getAboriginal() {
		return aboriginal;
	}
	public void setAboriginal(String aboriginal) {
		this.aboriginal = aboriginal;
	}
	public String getAboriginalOther() {
		return aboriginalOther;
	}
	public void setAboriginalOther(String aboriginalOther) {
		this.aboriginalOther = aboriginalOther;
	}
	public String getBirthCertificate() {
		return birthCertificate;
	}
	public void setBirthCertificate(String birthCertificate) {
		this.birthCertificate = birthCertificate;
	}
	public String getBirthCertificateYN() {
		return birthCertificateYN;
	}
	public void setBirthCertificateYN(String birthCertificateYN) {
		this.birthCertificateYN = birthCertificateYN;
	}
	public String getCitizenCardNo() {
		return citizenCardNo;
	}
	public void setCitizenCardNo(String citizenCardNo) {
		this.citizenCardNo = citizenCardNo;
	}
	public String getCitizenCardNoYN() {
		return citizenCardNoYN;
	}
	public void setCitizenCardNoYN(String citizenCardNoYN) {
		this.citizenCardNoYN = citizenCardNoYN;
	}
	public Demographic getClient() {
		return client;
	}
	public void setClient(Demographic client) {
		this.client = client;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getCurSleepArrangement() {
		return curSleepArrangement;
	}
	public void setCurSleepArrangement(String curSleepArrangement) {
		this.curSleepArrangement = curSleepArrangement;
	}
	public String getDisability() {
		return disability;
	}
	public void setDisability(String disability) {
		this.disability = disability;
	}
	public String getDisclosedAbuse() {
		return disclosedAbuse;
	}
	public void setDisclosedAbuse(String disclosedAbuse) {
		this.disclosedAbuse = disclosedAbuse;
	}
	public String getDisclosedAlcoholAbuse() {
		return disclosedAlcoholAbuse;
	}
	public void setDisclosedAlcoholAbuse(String disclosedAlcoholAbuse) {
		this.disclosedAlcoholAbuse = disclosedAlcoholAbuse;
	}
	public String getDisclosedMentalIssue() {
		return disclosedMentalIssue;
	}
	public void setDisclosedMentalIssue(String disclosedMentalIssue) {
		this.disclosedMentalIssue = disclosedMentalIssue;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getDriverLicenseNo() {
		return driverLicenseNo;
	}
	public void setDriverLicenseNo(String driverLicenseNo) {
		this.driverLicenseNo = driverLicenseNo;
	}
	public String getDriverLicenseNoYN() {
		return driverLicenseNoYN;
	}
	public void setDriverLicenseNoYN(String driverLicenseNoYN) {
		this.driverLicenseNoYN = driverLicenseNoYN;
	}
	public String getHealthCardNo() {
		return healthCardNo;
	}
	public void setHealthCardNo(String healthCardNo) {
		this.healthCardNo = healthCardNo;
	}
	public String getHealthCardNoYN() {
		return healthCardNoYN;
	}
	public void setHealthCardNoYN(String healthCardNoYN) {
		this.healthCardNoYN = healthCardNoYN;
	}
	public String getIdOther() {
		return idOther;
	}
	public void setIdOther(String idOther) {
		this.idOther = idOther;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getIncomeWorkerEmail1() {
		return incomeWorkerEmail1;
	}
	public void setIncomeWorkerEmail1(String incomeWorkerEmail1) {
		this.incomeWorkerEmail1 = incomeWorkerEmail1;
	}
	public String getIncomeWorkerEmail2() {
		return incomeWorkerEmail2;
	}
	public void setIncomeWorkerEmail2(String incomeWorkerEmail2) {
		this.incomeWorkerEmail2 = incomeWorkerEmail2;
	}
	public String getIncomeWorkerEmail3() {
		return incomeWorkerEmail3;
	}
	public void setIncomeWorkerEmail3(String incomeWorkerEmail3) {
		this.incomeWorkerEmail3 = incomeWorkerEmail3;
	}
	public String getIncomeWorkerName1() {
		return incomeWorkerName1;
	}
	public void setIncomeWorkerName1(String incomeWorkerName1) {
		this.incomeWorkerName1 = incomeWorkerName1;
	}
	public String getIncomeWorkerName2() {
		return incomeWorkerName2;
	}
	public void setIncomeWorkerName2(String incomeWorkerName2) {
		this.incomeWorkerName2 = incomeWorkerName2;
	}
	public String getIncomeWorkerName3() {
		return incomeWorkerName3;
	}
	public void setIncomeWorkerName3(String incomeWorkerName3) {
		this.incomeWorkerName3 = incomeWorkerName3;
	}
	public String getIncomeWorkerPhone1() {
		return incomeWorkerPhone1;
	}
	public void setIncomeWorkerPhone1(String incomeWorkerPhone1) {
		this.incomeWorkerPhone1 = incomeWorkerPhone1;
	}
	public String getIncomeWorkerPhone2() {
		return incomeWorkerPhone2;
	}
	public void setIncomeWorkerPhone2(String incomeWorkerPhone2) {
		this.incomeWorkerPhone2 = incomeWorkerPhone2;
	}
	public String getIncomeWorkerPhone3() {
		return incomeWorkerPhone3;
	}
	public void setIncomeWorkerPhone3(String incomeWorkerPhone3) {
		this.incomeWorkerPhone3 = incomeWorkerPhone3;
	}
	public String getInShelterBefore() {
		return inShelterBefore;
	}
	public void setInShelterBefore(String inShelterBefore) {
		this.inShelterBefore = inShelterBefore;
	}
	public LookupTagValue getLanguage() {
		return language;
	}
	public void setLanguage(LookupTagValue language) {
		this.language = language;
	}
	public String getLengthOfHomeless() {
		return lengthOfHomeless;
	}
	public void setLengthOfHomeless(String lengthOfHomeless) {
		this.lengthOfHomeless = lengthOfHomeless;
	}
	public String getLibraryCard() {
		return libraryCard;
	}
	public void setLibraryCard(String libraryCard) {
		this.libraryCard = libraryCard;
	}
	public String getLibraryCardYN() {
		return libraryCardYN;
	}
	public void setLibraryCardYN(String libraryCardYN) {
		this.libraryCardYN = libraryCardYN;
	}
	public String getLivedBefore() {
		return livedBefore;
	}
	public void setLivedBefore(String livedBefore) {
		this.livedBefore = livedBefore;
	}
	public String getLivedBeforeOther() {
		return livedBeforeOther;
	}
	public void setLivedBeforeOther(String livedBeforeOther) {
		this.livedBeforeOther = livedBeforeOther;
	}
	public String getNativeReserveNo() {
		return nativeReserveNo;
	}
	public void setNativeReserveNo(String nativeReserveNo) {
		this.nativeReserveNo = nativeReserveNo;
	}
	public String getNativeReserveNoYN() {
		return nativeReserveNoYN;
	}
	public void setNativeReserveNoYN(String nativeReserveNoYN) {
		this.nativeReserveNoYN = nativeReserveNoYN;
	}
	public String getObservedAbuse() {
		return observedAbuse;
	}
	public void setObservedAbuse(String observedAbuse) {
		this.observedAbuse = observedAbuse;
	}
	public String getObservedAlcoholAbuse() {
		return observedAlcoholAbuse;
	}
	public void setObservedAlcoholAbuse(String observedAlcoholAbuse) {
		this.observedAlcoholAbuse = observedAlcoholAbuse;
	}
	public String getObservedMentalIssue() {
		return observedMentalIssue;
	}
	public void setObservedMentalIssue(String observedMentalIssue) {
		this.observedMentalIssue = observedMentalIssue;
	}
	public OptionList getOptionList() {
		return optionList;
	}
	public void setOptionList(OptionList optionList) {
		this.optionList = optionList;
	}
	public LookupTagValue getOriginalCountry() {
		return originalCountry;
	}
	public void setOriginalCountry(LookupTagValue originalCountry) {
		this.originalCountry = originalCountry;
	}
	public String getPoorHygiene() {
		return poorHygiene;
	}
	public void setPoorHygiene(String poorHygiene) {
		this.poorHygiene = poorHygiene;
	}
	public String getPregnant() {
		return pregnant;
	}
	public void setPregnant(String pregnant) {
		this.pregnant = pregnant;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public List getProgramList() {
		return programList;
	}
	public void setProgramList(List programList) {
		this.programList = programList;
	}
	public String getReasonForHomeless() {
		return reasonForHomeless;
	}
	public void setReasonForHomeless(String reasonForHomeless) {
		this.reasonForHomeless = reasonForHomeless;
	}
	public String getReasonNoAdmit() {
		return reasonNoAdmit;
	}
	public void setReasonNoAdmit(String reasonNoAdmit) {
		this.reasonNoAdmit = reasonNoAdmit;
	}
	public String getRecordLanding() {
		return recordLanding;
	}
	public void setRecordLanding(String recordLanding) {
		this.recordLanding = recordLanding;
	}
	public String getRecordLandingYN() {
		return recordLandingYN;
	}
	public void setRecordLandingYN(String recordLandingYN) {
		this.recordLandingYN = recordLandingYN;
	}
	public String getReferredBy() {
		return referredBy;
	}
	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}
	public String getReferredTo() {
		return referredTo;
	}
	public void setReferredTo(String referredTo) {
		this.referredTo = referredTo;
	}
	public String getSIN() {
		return SIN;
	}
	public void setSIN(String sin) {
		SIN = sin;
	}
	public String getSINYN() {
		return SINYN;
	}
	public void setSINYN(String sinyn) {
		SINYN = sinyn;
	}
	public String getSourceIncome() {
		return sourceIncome;
	}
	public void setSourceIncome(String sourceIncome) {
		this.sourceIncome = sourceIncome;
	}
	public String getStatusInCanada() {
		return statusInCanada;
	}
	public void setStatusInCanada(String statusInCanada) {
		this.statusInCanada = statusInCanada;
	}
	public String getVAW() {
		return VAW;
	}
	public void setVAW(String vaw) {
		VAW = vaw;
	}
	public String getVeteranNo() {
		return veteranNo;
	}
	public void setVeteranNo(String veteranNo) {
		this.veteranNo = veteranNo;
	}
	public String getVeteranNoYN() {
		return veteranNoYN;
	}
	public void setVeteranNoYN(String veteranNoYN) {
		this.veteranNoYN = veteranNoYN;
	}
	public String getYouth() {
		return youth;
	}
	public void setYouth(String youth) {
		this.youth = youth;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getIntakeId() {
		return intakeId;
	}
	public void setIntakeId(String intakeId) {
		this.intakeId = intakeId;
	}
*/
    
}
