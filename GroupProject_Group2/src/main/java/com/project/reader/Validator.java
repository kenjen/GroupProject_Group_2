package com.project.reader;

import com.project.entities.*;

public interface Validator {
	
	boolean isValid(BaseData baseData);
	
	boolean isValid(EventCause eventCause);
	
	boolean isValid(FailureClass failureClass);
	
	boolean isValid(MccMnc mccMnc);
	
	boolean isValid(UE ue);

}
