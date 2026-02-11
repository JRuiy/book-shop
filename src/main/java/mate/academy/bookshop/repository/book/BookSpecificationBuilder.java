package mate.academy.bookshop.repository.book;

import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.BookSearchParameters;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.repository.SpecificationBuilder;
import mate.academy.bookshop.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> spec =
                (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            spec = spec.and(specificationProviderManager.findSpecification(SearchParam.TITLE)
                    .getSpecification(searchParameters.titles()));
        }
        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            spec = spec.and(specificationProviderManager.findSpecification(SearchParam.AUTHOR)
                    .getSpecification(searchParameters.authors()));
        }
        return spec;
    }
}
