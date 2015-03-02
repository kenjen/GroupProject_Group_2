package com.project.dao;

import java.util.Date;
import java.util.List;

public interface UeDAO {

	List<Object[]> getCallFailuresDateRange(Date start, Date end, Integer tac);

}
