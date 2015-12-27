package spring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dozac on 15/04/2015.
 */
@Entity
@Table(name="takvim")
public class HocaTakvim {

	@Id
	@GeneratedValue
    private int id;
    private int hocaid;
    private int ogrenciid;
    private Date startDate;
    private Date endDate;
    
    
    public HocaTakvim(){
    	
    }

    public HocaTakvim(int eventId) {
		this.id = eventId;
	}

	
    @Column(name="ID", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="HOCAID", nullable = false)
    public int getHocaid() {
        return hocaid;
    }

    public void setHocaid(int id) {
        this.hocaid = id;
    }

    @Column(name="OGRENCIID", nullable = true)
    public int getOgrenciid() {
        return ogrenciid;
    }

    public void setOgrenciid(int id) {
        this.ogrenciid = id;
    }
    
    
    @Column(name="STARTDATE", nullable = false)
    public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name="ENDDATE", nullable = false)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
    public String toString() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("id : ").append(getId());
        strBuff.append(", name : ").append(getHocaid());
        strBuff.append(", surname : ").append(getOgrenciid());
        return strBuff.toString();
    }

}
