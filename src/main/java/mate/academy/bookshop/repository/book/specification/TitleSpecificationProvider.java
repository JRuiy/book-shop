package mate.academy.bookshop.repository.book.specification;

import java.util.Arrays;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.repository.SpecificationProvider;
import mate.academy.bookshop.repository.book.SearchParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    private static final String TITLE_COLUMN = "title";

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(Arrays.stream(params)
                    .map(param -> criteriaBuilder
                            .like(criteriaBuilder.lower(root.get(TITLE_COLUMN)),
                                    "%" + param.toLowerCase() + "%"))
                    .toList());
    }

    @Override
    public SearchParam getSearchParam() {
        return SearchParam.TITLE;
    }
}
