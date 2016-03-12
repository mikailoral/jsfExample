package managedController;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.springframework.dao.DataAccessException;

import spring.model.Customer;
import spring.model.HocaTakvim;
import spring.model.OdemeBilgisi;
import spring.service.HocaTakvimService;
import common.MyEvent;
import common.StaticConstant;

@ManagedBean(name = "scheduleView")
@ViewScoped
public class ScheduleView implements Serializable {

	private ScheduleModel eventModel;

	private ScheduleModel lazyEventModel;

	private MyEvent event = new MyEvent();
	private OdemeBilgisi odeme = new OdemeBilgisi();
	List<OdemeBilgisi> listOdeme ;
	private static final String SUCCESS = "success";
	private static final String ERROR   = "error";
	
	@ManagedProperty(value = "#{HocaTakvimService}")
	HocaTakvimService hocaTakvimService;

	@PostConstruct
	public void init() {
		doldur();
	}

	public void doldur() {
		eventModel = new DefaultScheduleModel();
		List<HocaTakvim> list = hocaTakvimService.getHocaTakvims();
		listOdeme = hocaTakvimService.getoOdemes();

		for (HocaTakvim hocaTakvim : list) {
			event = new MyEvent("Uygun", hocaTakvim.getStartDate(),
					hocaTakvim.getEndDate());
			event.setEventId(hocaTakvim.getId());
			event.setHocaid(hocaTakvim.getHocaid());
			event.setOgrenciid(hocaTakvim.getOgrenciid());
			if (hocaTakvim.getOgrenciid() > 0) {
				event.setTitle("Dolu");
			}
			eventModel.addEvent(event);
		}
	}
	
	public String updateOdeme(OdemeBilgisi odeme) {
        try {
        	hocaTakvimService.updateOdeme(odeme);
//            getCustomerService().updateCustomer(customer);
            return SUCCESS;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return ERROR;
    }

	public void onEventSelect(SelectEvent selectEvent) {

		int userid = (Integer) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("userid");
		int roleid = (Integer) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("roleid");

		System.out.println(userid + ":" + roleid);

		FacesMessage message;
		event = (MyEvent) selectEvent.getObject();
		System.out.println("event id : " + event.getEventId());
		if (roleid == 2) {
			if (event.getOgrenciid() > 0) {
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hata",
						"Lütfen iptal için yöneticiye başvurunuz");
				addMessage(message);
			} else {
				int sonuc = hocaTakvimService.deleteHocaTakvim(new HocaTakvim(
						event.getEventId()));
				if (sonuc >= 0) {
					eventModel.deleteEvent(event);
					message = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"OK", "Saat kaldırıldı");
					addMessage(message);
					doldur();
				} else {
					message = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Hata", "Silinemedi");
					addMessage(message);
				}
			}
		} else if (roleid == 3) {

			if (event.getOgrenciid() == userid) {
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hata",
						"Lütfen iptal için sisteme mail atınız");
				addMessage(message);
			} else if (event.getOgrenciid() > 0) {
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hata",
						"Bu saat doludur.");
				addMessage(message);
				doldur();
			} else {
				HocaTakvim hocaTakvim = new HocaTakvim();
				hocaTakvim.setId(event.getEventId());
				hocaTakvim.setStartDate(event.getStartDate());
				hocaTakvim.setEndDate(event.getEndDate());
				hocaTakvim.setHocaid(event.getHocaid());
				hocaTakvim.setOgrenciid(userid);
				hocaTakvimService.updateHocaTakvim(hocaTakvim);
				event.setOgrenciid(userid);
				eventModel.updateEvent(event);

				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "OK",
						"Rezarvasyonunuz alınmıştır. Teşekkürler.");
				addMessage(message);
				doldur();

			}
		}
	}

	public void onDateSelect(SelectEvent selectEvent) {
		int userid = (Integer) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("userid");
		int roleid = (Integer) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("roleid");
		System.out.println(userid + ":" + roleid);
		FacesMessage message;

		Calendar cal = Calendar.getInstance();
		cal.setTime((Date) selectEvent.getObject());
		cal.add(Calendar.HOUR_OF_DAY, 1);
		event = new MyEvent("Uygun", (Date) selectEvent.getObject(),
				(cal.getTime()));

		Date d = new Date();
		System.out.println(d.getTime());
		System.out.println(((Date) selectEvent.getObject()).getTime());
		System.out.println((d.getTime() + ( 2592000000L ) ));
		if (d.getTime() > ((Date) selectEvent.getObject()).getTime()) {
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hata",
					"Eski tarihe atama yapılamaz");
			addMessage(message);
		}else if ((d.getTime() + ( 2592000000L ) ) < ((Date) selectEvent.getObject()).getTime()) {
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hata",
					"Eski tarihe atama yapılamaz");
			addMessage(message);
		} else {
			HocaTakvim hocaTakvim = new HocaTakvim();
			if (roleid == 2) {
				hocaTakvim.setHocaid(userid);
				hocaTakvim.setStartDate((Date) selectEvent.getObject());
				hocaTakvim.setEndDate(cal.getTime());
				int hocaTakvimId = hocaTakvimService.addHocaTakvim(hocaTakvim);
				if (hocaTakvimId > 0) {
					message = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"OK", "Saat Eklendi");
					addMessage(message);
					event.setEventId(hocaTakvimId);
					eventModel.addEvent(event);
					doldur();
				}
			} else {
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						"Uygun hoca yoktur. Lütfen başka zaman dilimi seçiniz");
				addMessage(message);
			}
		}
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public HocaTakvimService getHocaTakvimService() {
		return hocaTakvimService;
	}

	public void setHocaTakvimService(HocaTakvimService hocaTakvimService) {
		this.hocaTakvimService = hocaTakvimService;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}

	public MyEvent getEvent() {
		return event;
	}

	public void setEvent(MyEvent event) {
		this.event = event;
	}

	public OdemeBilgisi getOdeme() {
		return odeme;
	}

	public void setOdeme(OdemeBilgisi odeme) {
		this.odeme = odeme;
	}

	public List<OdemeBilgisi> getListOdeme() {
		return listOdeme;
	}

	public void setListOdeme(List<OdemeBilgisi> listOdeme) {
		this.listOdeme = listOdeme;
	}
	
	

}