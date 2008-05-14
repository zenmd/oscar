package org.oscarehr.survey.model.oscar;

public class OscarFormQuestion {

	private Integer id;
	private Integer page;
	private Integer section;
	private Integer question;
	private String description;
	private Integer formId;
	private String formQuestionId;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSection() {
		return section;
	}
	public void setSection(Integer section) {
		this.section = section;
	}
	public Integer getQuestion() {
		return question;
	}
	public void setQuestion(Integer question) {
		this.question = question;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getFormId() {
		return formId;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	public String getFormQuestionId() {
		return formQuestionId;
	}
	public void setFormQuestionId(String formQuestionId) {
		this.formQuestionId = formQuestionId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
