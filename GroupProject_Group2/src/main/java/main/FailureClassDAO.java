package main;

import java.util.List;

import persistence.PersistenceUtil;
import entity.FailureClass;

public class FailureClassDAO 
{
	public static void main(String[] args) 
	{
		FailureClassDAO config = new FailureClassDAO();
	}

	public FailureClassDAO() 
	{
		createFailure(1, "Emergency");
		viewFailure();
	}

	public void viewFailure() 
	{
		List<FailureClass> failures = PersistenceUtil.findAllFailures();
		for (FailureClass f : failures) 
		{
			System.out.println("Failure " + f.getId() + " exists.");
		}
	}

	public void createFailure(int failureClass, String desc) 
	{
		FailureClass failure = new FailureClass(failureClass, desc);
		PersistenceUtil.persist(failure);
		System.out.println("Failure created");
	}

}
