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

public class QuatroComplaintOutcome {
   private String outcomeSatisfied;
   private String standardBreachedYN;
   private String outstandingIssueYN;
   private String outstandingIssue;
   private String complaintStatus;
   private String dateCompleted;
   private String timeSpent;
   private String reviewName1;
   private String reviewTitle1;
   private String reviewDate1;
   private String reviewName2;
   private String reviewTitle2;
   private String reviewDate2;
   private String reviewName3;
   private String reviewTitle3;
   private String reviewDate3;
   private String reviewName4;
   private String reviewTitle4;
   private String reviewDate4;

   public String getComplaintStatus() {
	return complaintStatus;
   }
   public void setComplaintStatus(String complaintStatus) {
	this.complaintStatus = complaintStatus;
   }
   public String getDateCompleted() {
	return dateCompleted;
   }
   public void setDateCompleted(String dateCompleted) {
	this.dateCompleted = dateCompleted;
   }
   public String getOutcomeSatisfied() {
	return outcomeSatisfied;
   }
   public void setOutcomeSatisfied(String outcomeSatisfied) {
	this.outcomeSatisfied = outcomeSatisfied;
   }
  public String getOutstandingIssue() {
	return outstandingIssue;
  }
  public void setOutstandingIssue(String outstandingIssue) {
	this.outstandingIssue = outstandingIssue;
  }
  public String getOutstandingIssueYN() {
	return outstandingIssueYN;
  }
  public void setOutstandingIssueYN(String outstandingIssueYN) {
	this.outstandingIssueYN = outstandingIssueYN;
  }
  public String getReviewDate1() {
	return reviewDate1;
  }
  public void setReviewDate1(String reviewDate1) {
	this.reviewDate1 = reviewDate1;
  }
  public String getReviewDate2() {
	return reviewDate2;
  }
  public void setReviewDate2(String reviewDate2) {
	this.reviewDate2 = reviewDate2;
  }
  public String getReviewDate3() {
	return reviewDate3;
  }
  public void setReviewDate3(String reviewDate3) {
	this.reviewDate3 = reviewDate3;
  }
  public String getReviewDate4() {
	return reviewDate4;
  }
  public void setReviewDate4(String reviewDate4) {
	this.reviewDate4 = reviewDate4;
  }
  public String getReviewName1() {
	return reviewName1;
  }
  public void setReviewName1(String reviewName1) {
	this.reviewName1 = reviewName1;
  }
  public String getReviewName2() {
	return reviewName2;
  }
  public void setReviewName2(String reviewName2) {
	this.reviewName2 = reviewName2;
  }
  public String getReviewName3() {
	return reviewName3;
  }
  public void setReviewName3(String reviewName3) {
	this.reviewName3 = reviewName3;
  }
  public String getReviewName4() {
	return reviewName4;
  }
  public void setReviewName4(String reviewName4) {
	this.reviewName4 = reviewName4;
  }
  public String getReviewTitle1() {
	return reviewTitle1;
  }
  public void setReviewTitle1(String reviewTitle1) {
	this.reviewTitle1 = reviewTitle1;
  }
  public String getReviewTitle2() {
	return reviewTitle2;
  }
  public void setReviewTitle2(String reviewTitle2) {
	this.reviewTitle2 = reviewTitle2;
  }
  public String getReviewTitle3() {
	return reviewTitle3;
  }
  public void setReviewTitle3(String reviewTitle3) {
	this.reviewTitle3 = reviewTitle3;
  }
  public String getReviewTitle4() {
	return reviewTitle4;
  }
  public void setReviewTitle4(String reviewTitle4) {
	this.reviewTitle4 = reviewTitle4;
  }
  public String getStandardBreachedYN() {
	return standardBreachedYN;
  }
  public void setStandardBreachedYN(String standardBreachedYN) {
	this.standardBreachedYN = standardBreachedYN;
  }
  public String getTimeSpent() {
	return timeSpent;
  }
  public void setTimeSpent(String timeSpent) {
	this.timeSpent = timeSpent;
  }
}
