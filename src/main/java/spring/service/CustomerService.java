package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.dao.CustomerDAO;
import spring.model.Customer;

import java.util.List;

/**
 * Created by dozac on 15/04/2015.
 */
@Service("CustomerService")
@Transactional(readOnly = true)
public class CustomerService {

    @Autowired
    CustomerDAO customerDAO;

    /*** Annotation of applying method level Spring Security ***/
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = false)
    public void addCustomer(Customer customer) {
        getCustomerDAO().addCustomer(customer);
    }

    @Transactional(readOnly = false)
    public void deleteCustomer(Customer customer) {
        getCustomerDAO().deleteCustomer(customer);
    }

    @Transactional(readOnly = false)
    public void updateCustomer(Customer customer) {
        getCustomerDAO().updateCustomer(customer);
    }

    public Customer getCustomerById(int id) {
        return getCustomerDAO().getCustomerById(id);
    }

    public List<Customer> getCustomers() {
        return getCustomerDAO().getCustomers();
    }

    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

}