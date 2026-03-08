package org.example.uniproject.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.example.uniproject.dto.UserFilter;
import org.example.uniproject.entity.User;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> userFilter(UserFilter filter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter == null) {
                return criteriaBuilder.conjunction(); //when is empty
            }

            if (filter.getUsername() != null && !filter.getUsername().isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("username")),
                                "%" + filter.getUsername().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getFirstName() != null && !filter.getFirstName().isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("firstName")),
                                "%" + filter.getFirstName().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getLastName() != null && !filter.getLastName().isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("lastName")),
                                "%" + filter.getLastName().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getBirthDate() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("birthDate"), filter.getBirthDate())
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
