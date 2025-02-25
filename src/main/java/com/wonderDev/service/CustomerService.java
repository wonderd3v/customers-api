package com.wonderDev.service;

import com.wonderDev.client.CountryClient;
import com.wonderDev.dto.*;
import com.wonderDev.exception.CustomerNotfoundException;
import com.wonderDev.mapper.CustomerMapper;
import com.wonderDev.model.Customer;
import com.wonderDev.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class CustomerService {
    private static final Logger LOG = Logger.getLogger(CustomerService.class);

    @Inject
    CustomerRepository customerRepository;

    @Inject
    CountryClient countryClient;

    public List<CustomerDto> getAllCustomers() {
        LOG.info("Obteniendo todos los clientes");
        return customerRepository.listAll()
                .stream()
                .map(CustomerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomerById(Long id) {
        LOG.infof("Buscando cliente con ID: %d", id);
        return Optional.ofNullable(customerRepository.findById(id))
                .map(CustomerMapper::toDTO)
                .orElseThrow(() -> {
                    LOG.errorf("Cliente con ID %d no encontrado", id);
                    return new CustomerNotfoundException("Cliente con ID " + id + " no encontrado");
                });
    }

    public CustomerDto getCustomerByEmail(String email) {
        LOG.infof("Buscando cliente con correo electrónico: %s", email);
        return Optional.ofNullable(customerRepository.findByEmail(email))
                .map(CustomerMapper::toDTO)
                .orElseThrow(() -> {
                    LOG.errorf("Cliente con correo electrónico %s no encontrado", email);
                    return new CustomerNotfoundException("Cliente con correo electrónico " + email + " no encontrado");
                });
    }

    public ResourceResponse<List<CustomerDto>> getCustomersPaginated(int page, int size) {
        LOG.infof("Obteniendo clientes paginados: página=%d, tamaño=%d", page, size);
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        List<Customer> customers = customerRepository.findPaginated(page - 1, size);
        long totalItems = customerRepository.countCustomers();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        List<CustomerDto> customerDtos = customers.stream()
                .map(CustomerMapper::toDTO)
                .collect(Collectors.toList());

        LOG.infof("Se encontraron %d clientes (total de registros: %d, total de páginas: %d)", customerDtos.size(), totalItems, totalPages);
        return ResourceResponse.success("Clientes obtenidos exitosamente", customerDtos, new Pagination(page, size, totalItems, totalPages));
    }

    @Transactional
    public CustomerDto createCustomer(CreateCustomerDto dto) {
        LOG.infof("Creando nuevo cliente con correo electrónico: %s", dto.email);

        if (dto.email == null || dto.email.isEmpty()) {
            LOG.error("Intento de creación de cliente sin correo electrónico");
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío");
        }

        if (customerRepository.findByEmail(dto.email) != null) {
            LOG.errorf("Ya existe un cliente con el correo electrónico %s", dto.email);
            throw new IllegalArgumentException("Ya existe un cliente con este correo electrónico");
        }

        Customer customer = CustomerMapper.fromCreateDTO(dto);
        customer.demonym = countryClient.getDemonymByCountryCode(dto.country);

        customerRepository.persist(customer);
        LOG.infof("Cliente creado exitosamente con ID: %d", customer.id);

        return CustomerMapper.toDTO(customer);
    }

    @Transactional
    public CustomerDto updateCustomer(Long id, UpdateCustomerDto dto) {
        LOG.infof("Actualizando cliente con ID: %d", id);
        Customer customer = customerRepository.findById(id);
        if (customer == null) {
            LOG.errorf("Cliente con ID %d no encontrado para actualización", id);
            throw new CustomerNotfoundException("Cliente con ID " + id + " no encontrado");
        }

        CustomerMapper.updateEntity(customer, dto);

        if (dto.country != null) {
            customer.demonym = countryClient.getDemonymByCountryCode(dto.country);
        }

        LOG.infof("Cliente con ID %d actualizado exitosamente", id);
        return CustomerMapper.toDTO(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        LOG.infof("Eliminando cliente con ID: %d", id);
        boolean deleted = customerRepository.deleteById(id);
        if (!deleted) {
            LOG.errorf("Cliente con ID %d no encontrado para eliminación", id);
            throw new CustomerNotfoundException("Cliente con ID " + id + " no encontrado");
        }

        LOG.infof("Cliente con ID %d eliminado exitosamente", id);
    }
}
