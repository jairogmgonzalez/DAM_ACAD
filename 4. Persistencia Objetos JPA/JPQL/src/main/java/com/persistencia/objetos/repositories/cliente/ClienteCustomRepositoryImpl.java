package com.persistencia.objetos.repositories.cliente;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.persistencia.objetos.entities.Cliente;
import com.persistencia.objetos.entities.Direccion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class ClienteCustomRepositoryImpl implements ClienteCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cliente> buscarClientesPorNombreOEmail(String texto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
        Root<Cliente> cliente = cq.from(Cliente.class);

        Predicate condicionNombre = cb.like(cliente.get("nombre"), "%" + texto + "%");
        Predicate condicionEmail = cb.like(cliente.get("email"), "%" + texto + "%");
        cq.where(cb.or(condicionNombre, condicionEmail));

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Cliente> buscarClientesPorNombreOrdenadosPorEmail(String nombre, boolean ascendente) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
        Root<Cliente> cliente = cq.from(Cliente.class);

        cq.where(cb.like(cliente.get("nombre"), "%" + nombre + "%"));

        if (ascendente) {
            cq.orderBy(cb.asc(cliente.get("email")));
        } else {
            cq.orderBy(cb.desc(cliente.get("email")));
        }

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Object[]> contarClientesPorCiudad() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Cliente> cliente = cq.from(Cliente.class);

        Join<Cliente, Direccion> direccion = cliente.join("direccion", JoinType.INNER);

        cq.multiselect(direccion.get("ciudad"), cb.count(cliente));
        cq.groupBy(direccion.get("ciudad"));

        return entityManager.createQuery(cq).getResultList();
    }
}

