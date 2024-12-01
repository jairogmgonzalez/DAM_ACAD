package com.persistencia.objetos.repositories.direccion;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.persistencia.objetos.entities.Direccion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class DireccionCustomRepositoryImpl implements DireccionCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Direccion> buscarDireccionesPorCiudadOCodigoPostal(String ciudad, Integer codigoPostal) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Direccion> cq = cb.createQuery(Direccion.class);
        Root<Direccion> direccion = cq.from(Direccion.class);

        Predicate condicionCiudad = ciudad != null ? cb.like(direccion.get("ciudad"), "%" + ciudad + "%") : cb.conjunction();
        Predicate condicionCodigoPostal = codigoPostal != null ? cb.equal(direccion.get("codigoPostal"), codigoPostal) : cb.conjunction();

        cq.where(cb.or(condicionCiudad, condicionCodigoPostal));

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Direccion> buscarDireccionesOrdenadasPor(String campoOrden, boolean ascendente) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Direccion> cq = cb.createQuery(Direccion.class);
        Root<Direccion> direccion = cq.from(Direccion.class);

        if (ascendente) {
            cq.orderBy(cb.asc(direccion.get(campoOrden)));
        } else {
            cq.orderBy(cb.desc(direccion.get(campoOrden)));
        }

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Object[]> contarDireccionesPorCiudad() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Direccion> direccion = cq.from(Direccion.class);

        cq.multiselect(direccion.get("ciudad"), cb.count(direccion));
        cq.groupBy(direccion.get("ciudad"));

        return entityManager.createQuery(cq).getResultList();
    }
}
