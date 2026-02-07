package mate.academy.bookshop.repository;

import mate.academy.bookshop.repository.book.SearchParam;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    Specification<T> getSpecification(String[] params);

    SearchParam getSearchParam();
}
