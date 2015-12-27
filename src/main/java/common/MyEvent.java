package common;

import java.util.Date;

import org.primefaces.model.DefaultScheduleEvent;

public class MyEvent extends DefaultScheduleEvent {
	
	private static final long serialVersionUID = 1L;
	private int eventId;
	private int hocaid;
    private int ogrenciid;

	public MyEvent(){
		super();
	}
	public MyEvent(String string, Date startDate, Date endDate) {
		super(string, startDate, endDate);
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public int getHocaid() {
		return hocaid;
	}
	public void setHocaid(int hocaid) {
		this.hocaid = hocaid;
	}
	public int getOgrenciid() {
		return ogrenciid;
	}
	public void setOgrenciid(int ogrenciid) {
		this.ogrenciid = ogrenciid;
	}
	
	
	

}
