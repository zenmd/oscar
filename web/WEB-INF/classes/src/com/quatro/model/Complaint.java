package com.quatro.model;

import java.util.Date;


/**
 * Complaint entity. @author MyEclipse Persistence Tools
 */

public class Complaint  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String source;
     private String method;
     private String firstname;
     private String lastname;
     private String standards;
     private String description;
     private String satisfiedWithOutcome;
     private String standardsBreached;
     private String outstandingIssues;
     private String status;
     private Date completedDate;
     private Date createdDate;
     private String duration;
     private String person1;
     private String title1;
     private Date date1;
     private String person2;
     private String title2;
     private Date date2;
     private String person3;
     private String title3;
     private Date date3;
     private String person4;
     private String title4;
     private Date date4;
     private Long clientId;
     private Long programId;


    // Constructors

    /** default constructor */
    public Complaint() {
    }

    
    /** full constructor */
    public Complaint(String source, String method, String firstname, String lastname, String standards, String description, String satisfiedWithOutcome, String standardsBreached, String outstandingIssues, String status, Date completedDate, Date createdDate, String duration, String person1, String title1, Date date1, String person2, String title2, Date date2, String person3, String title3, Date date3, String person4, String title4, Date date4, Long clientId, Long programId) {
        this.source = source;
        this.method = method;
        this.firstname = firstname;
        this.lastname = lastname;
        this.standards = standards;
        this.description = description;
        this.satisfiedWithOutcome = satisfiedWithOutcome;
        this.standardsBreached = standardsBreached;
        this.outstandingIssues = outstandingIssues;
        this.status = status;
        this.completedDate = completedDate;
        this.createdDate = createdDate;
        this.duration = duration;
        this.person1 = person1;
        this.title1 = title1;
        this.date1 = date1;
        this.person2 = person2;
        this.title2 = title2;
        this.date2 = date2;
        this.person3 = person3;
        this.title3 = title3;
        this.date3 = date3;
        this.person4 = person4;
        this.title4 = title4;
        this.date4 = date4;
        this.clientId = clientId;
        this.programId = programId;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return this.source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }

    public String getMethod() {
        return this.method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }

    public String getFirstname() {
        return this.firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStandards() {
        return this.standards;
    }
    
    public void setStandards(String standards) {
        this.standards = standards;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getSatisfiedWithOutcome() {
        return this.satisfiedWithOutcome;
    }
    
    public void setSatisfiedWithOutcome(String satisfiedWithOutcome) {
        this.satisfiedWithOutcome = satisfiedWithOutcome;
    }

    public String getStandardsBreached() {
        return this.standardsBreached;
    }
    
    public void setStandardsBreached(String standardsBreached) {
        this.standardsBreached = standardsBreached;
    }

    public String getOutstandingIssues() {
        return this.outstandingIssues;
    }
    
    public void setOutstandingIssues(String outstandingIssues) {
        this.outstandingIssues = outstandingIssues;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCompletedDate() {
        return this.completedDate;
    }
    
    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDuration() {
        return this.duration;
    }
    
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPerson1() {
        return this.person1;
    }
    
    public void setPerson1(String person1) {
        this.person1 = person1;
    }

    public String getTitle1() {
        return this.title1;
    }
    
    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public Date getDate1() {
        return this.date1;
    }
    
    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public String getPerson2() {
        return this.person2;
    }
    
    public void setPerson2(String person2) {
        this.person2 = person2;
    }

    public String getTitle2() {
        return this.title2;
    }
    
    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public Date getDate2() {
        return this.date2;
    }
    
    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public String getPerson3() {
        return this.person3;
    }
    
    public void setPerson3(String person3) {
        this.person3 = person3;
    }

    public String getTitle3() {
        return this.title3;
    }
    
    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public Date getDate3() {
        return this.date3;
    }
    
    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    public String getPerson4() {
        return this.person4;
    }
    
    public void setPerson4(String person4) {
        this.person4 = person4;
    }

    public String getTitle4() {
        return this.title4;
    }
    
    public void setTitle4(String title4) {
        this.title4 = title4;
    }

    public Date getDate4() {
        return this.date4;
    }
    
    public void setDate4(Date date4) {
        this.date4 = date4;
    }

    public Long getClientId() {
        return this.clientId;
    }
    
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getProgramId() {
        return this.programId;
    }
    
    public void setProgramId(Long programId) {
        this.programId = programId;
    }
   








}