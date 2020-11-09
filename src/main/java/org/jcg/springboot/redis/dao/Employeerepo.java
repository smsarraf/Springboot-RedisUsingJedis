package org.jcg.springboot.redis.dao;

import java.util.Map;

import org.jcg.springboot.redis.model.Employee;

public interface Employeerepo {

	// Save a new employee.
	void save(Employee employee);
	
	// Find employee by id.
	Employee findById(String id);
	
	// Final all employees.
	Map<String, Employee> findAll();
	
	// Delete a employee.
	void delete(String id);
}
