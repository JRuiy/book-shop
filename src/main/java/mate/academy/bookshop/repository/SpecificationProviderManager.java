package mate.academy.bookshop.repository;

import mate.academy.bookshop.repository.book.SearchParam;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> findSpecification(SearchParam param);
}
