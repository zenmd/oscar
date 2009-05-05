/*******************************************************************************
 * Copyright (c) 2008, 2009 Quatro Group Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License
 * which accompanies this distribution, and is available at
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * Contributors:
 *     <Quatro Group Software Systems inc.>  <OSCAR Team>
 *******************************************************************************/
package org.oscarehr.PMmodule.model;

import java.util.List;

public class QuatroComplaint {
  private String sourceComplaint;
  private String methodContact;
  private String sourceFirstName;
  private String sourceLastName;
  private String shelterStdYN;
  private List organizationStandards;
  private String[] organizationStandardIds;
  private List accessToShelters;
  private String[] accessToShelterIds;
  private List residentRights;
  private String[] residentRightIds;
  private List programStandards;
  private String[] programStandardIds;
  private String foodSafetyNutritionYN;
  private List healthSafetys;
  private String[] healthSafetyIds;
  private String staffTrainingYN;
  private String descComplaint;

  public String[] getAccessToShelterIds() {
	return accessToShelterIds;
  }
  public void setAccessToShelterIds(String[] accessToShelterIds) {
	this.accessToShelterIds = accessToShelterIds;
  }
  public List getAccessToShelters() {
	return accessToShelters;
  }
  public void setAccessToShelters(List accessToShelters) {
	this.accessToShelters = accessToShelters;
  }
  public String getDescComplaint() {
	return descComplaint;
  }
  public void setDescComplaint(String descComplaint) {
	this.descComplaint = descComplaint;
  }
  public String getFoodSafetyNutritionYN() {
	return foodSafetyNutritionYN;
  }
  public void setFoodSafetyNutritionYN(String foodSafetyNutritionYN) {
	this.foodSafetyNutritionYN = foodSafetyNutritionYN;
  }
  public String[] getHealthSafetyIds() {
	return healthSafetyIds;
  }
  public void setHealthSafetyIds(String[] healthSafetyIds) {
	this.healthSafetyIds = healthSafetyIds;
  }
  public List getHealthSafetys() {
	return healthSafetys;
  }
  public void setHealthSafetys(List healthSafetys) {
	this.healthSafetys = healthSafetys;
  }
  public String getMethodContact() {
	return methodContact;
  }
  public void setMethodContact(String methodContact) {
	this.methodContact = methodContact;
  }
  public String[] getOrganizationStandardIds() {
	return organizationStandardIds;
  }
  public void setOrganizationStandardIds(String[] organizationStandardIds) {
	this.organizationStandardIds = organizationStandardIds;
  }
  public List getOrganizationStandards() {
	return organizationStandards;
  }
  public void setOrganizationStandards(List organizationStandards) {
	this.organizationStandards = organizationStandards;
  }
  public String[] getProgramStandardIds() {
	return programStandardIds;
  }
  public void setProgramStandardIds(String[] programStandardIds) {
	this.programStandardIds = programStandardIds;
  }
  public List getProgramStandards() {
	return programStandards;
  }
  public void setProgramStandards(List programStandards) {
	this.programStandards = programStandards;
  }
  public String[] getResidentRightIds() {
	return residentRightIds;
  }
  public void setResidentRightIds(String[] residentRightIds) {
	this.residentRightIds = residentRightIds;
  }
  public List getResidentRights() {
	return residentRights;
  }
  public void setResidentRights(List residentRights) {
	this.residentRights = residentRights;
  }
  public String getShelterStdYN() {
	return shelterStdYN;
  }
  public void setShelterStdYN(String shelterStdYN) {
	this.shelterStdYN = shelterStdYN;
  }
  public String getSourceComplaint() {
	return sourceComplaint;
  }
  public void setSourceComplaint(String sourceComplaint) {
	this.sourceComplaint = sourceComplaint;
  }
  public String getSourceFirstName() {
	return sourceFirstName;
  }
  public void setSourceFirstName(String sourceFirstName) {
	this.sourceFirstName = sourceFirstName;
  }
  public String getSourceLastName() {
	return sourceLastName;
  }
  public void setSourceLastName(String sourceLastName) {
	this.sourceLastName = sourceLastName;
  }
  public String getStaffTrainingYN() {
	return staffTrainingYN;
  }
  public void setStaffTrainingYN(String staffTrainingYN) {
	this.staffTrainingYN = staffTrainingYN;
  }

}
