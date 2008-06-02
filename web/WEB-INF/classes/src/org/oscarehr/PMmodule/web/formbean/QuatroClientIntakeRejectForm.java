package org.oscarehr.PMmodule.web.formbean;

import org.apache.struts.validator.ValidatorForm;
import java.util.List;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.BedDemographicPK;
import org.oscarehr.PMmodule.model.QuatroIntake;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.model.RoomDemographicPK;


public class QuatroClientIntakeRejectForm extends ValidatorForm{

    private List rejectReasonList;
    private String rejectReason;
    private String rejectNote;
    private Integer queueId;

    public String getRejectNote() {
		return rejectNote;
	}
	public void setRejectNote(String rejectNote) {
		this.rejectNote = rejectNote;
	}
	public List getRejectReasonList() {
		return rejectReasonList;
	}
	public void setRejectReasonList(List rejectReasonList) {
		this.rejectReasonList = rejectReasonList;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public Integer getQueueId() {
		return queueId;
	}
	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}
	
}
