package com.habibe.hotelautomationmaven.model;

import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDAOTest {

    private static Customer testCustomer;

    @BeforeEach
    public void setup() {
        testCustomer = new Customer();
        testCustomer.setName("JUnit" + System.currentTimeMillis()); 
        testCustomer.setSurname("Test");
        testCustomer.setPhone("000000" + (int)(Math.random() * 10000));
        testCustomer.setEmail("junit" + System.currentTimeMillis() + "@example.com");
    }

    @Test
    public void testAddCustomer() {
        boolean result = CustomerDAO.addCustomer(testCustomer);
        assertTrue(result, "Customer should be added successfully");
    }

    @Test
    public void testIsCustomerExists() {
        CustomerDAO.addCustomer(testCustomer);
        boolean exists = CustomerDAO.isCustomerExists(testCustomer.getPhone(), testCustomer.getEmail());
        assertTrue(exists, "Customer should exist after being added");
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = CustomerDAO.getAllCustomers();
        assertNotNull(customers, "Customer list should not be null");
    }

    @Test
    public void testGetCustomerById() {
        CustomerDAO.addCustomer(testCustomer);
        List<Customer> customers = CustomerDAO.getAllCustomers();
        Customer last = customers.get(customers.size() - 1);
        Customer found = CustomerDAO.getCustomerById(last.getId());
        assertNotNull(found);
        assertEquals(last.getName(), found.getName());
    }

    @AfterEach
    public void cleanup() {
        List<Customer> all = CustomerDAO.getAllCustomers();
        for (Customer c : all) {
            if (c.getEmail().startsWith("junit") || c.getPhone().startsWith("000000")) {
                CustomerDAO.deleteCustomer(c.getId());
            }
        }
    }
}
