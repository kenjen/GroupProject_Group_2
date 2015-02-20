package com.project.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.BaseData;
import com.project.entities.FailureClass;
import com.project.entities.UE;

@Stateless
@Local
public class JPABaseDataDAO implements BaseDataDAO {
	
	@EJB
	private UserEquipmentDAO ueDAO;
	@EJB
	private EventCauseDAO eventCauseDAO;
	@EJB
	FailureClassDAOLocal failurClassDAO;


	@PersistenceContext(unitName="GroupProject_Group2") EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public Collection<BaseData> getAllBaseData() {
		Query query = entityManager.createNamedQuery("BaseData.getAllBaseData");
		List<BaseData> data = query.getResultList();
		return data;
	}

	@SuppressWarnings("rawtypes")
	public void addAllBaseData(Collection baseDataList) {
		entityManager.createNativeQuery("truncate table base_data").executeUpdate();
		entityManager.createNativeQuery("alter table base_data AUTO_INCREMENT = 1").executeUpdate();
		for (Object o : baseDataList) {
			entityManager.persist(o);
		}
		addUEForeignKey();
		addFailureClassForeignKey();
	}
	
	public void addUEForeignKey(){
		
		Collection<BaseData> allBaseData = this.getAllBaseData();
		List<UE> allUE = (List<UE>) ueDAO.getAllUEs();
		for (BaseData o : allBaseData) {
			for(UE ue : allUE){
				if((int)o.getTac() == (int)ue.getTac()){
					o.setUeFK(ue);
				}
				else{
					//NB - FOR TEST ONLY
					o.setUeFK(allUE.get(allUE.size()-1));
				}
			}
		}
	}
	
	public void addFailureClassForeignKey(){
		Collection<BaseData> allBaseData = this.getAllBaseData();
		List<FailureClass> failureClasses = (List<FailureClass>) failurClassDAO.getAllFailureClasses();
		for (BaseData o : allBaseData) {
			for(FailureClass fc : failureClasses){
				if((int)o.getFailureClass() == (int)fc.getFailureClass()-1){
					o.setFaliureClassFK(fc);
				}
				else{
					//NB - FOR TEST ONLY
					o.setFaliureClassFK(failureClasses.get(failureClasses.size()-1));
				}
			}
		}
	}

}
