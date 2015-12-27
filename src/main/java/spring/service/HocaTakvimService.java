package spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.dao.HocaTakvimDAO;
import spring.model.HocaTakvim;
import spring.model.Odeme;

/**
 * Created by dozac on 15/04/2015.
 */
@Service("HocaTakvimService")
@Transactional(readOnly = true)
public class HocaTakvimService {

    @Autowired
    HocaTakvimDAO hocaTakvimDAO;

    /*** Annotation of applying method level Spring Security ***/
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER') ")
    @Transactional(readOnly = false)
    public int addHocaTakvim(HocaTakvim hocaTakvim) {
        return getHocaTakvimDAO().addHocaTakvim(hocaTakvim);
    }

    @Transactional(readOnly = false)
    public int deleteHocaTakvim(HocaTakvim hocaTakvim) {
        return getHocaTakvimDAO().deleteHocaTakvim(hocaTakvim);
    }

    @Transactional(readOnly = false)
    public void updateHocaTakvim(HocaTakvim hocaTakvim) {
        getHocaTakvimDAO().updateHocaTakvim(hocaTakvim);
    }
    
    @Transactional(readOnly = false)
	public void updateOdeme(Odeme odeme) {
    	getHocaTakvimDAO().updateOdeme(odeme);
		
	}

    public HocaTakvim getHocaTakvimById(int id) {
        return getHocaTakvimDAO().getHocaTakvimById(id);
    }

    public List<HocaTakvim> getHocaTakvims() {
        return getHocaTakvimDAO().getHocaTakvims();
    }

    public List<Odeme> getoOdemes(){
    	return getHocaTakvimDAO().getOdemes();
    }

    public HocaTakvimDAO getHocaTakvimDAO() {
        return hocaTakvimDAO;
    }

    public void setHocaTakvimDAO(HocaTakvimDAO hocaTakvimDAO) {
        this.hocaTakvimDAO = hocaTakvimDAO;
    }

}