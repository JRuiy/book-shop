package mate.academy.bookshop.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.repository.book.SearchParam;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpecificationProviderManagerImpl implements SpecificationProviderManager<Book> {

    private final List<SpecificationProvider<Book>> specificationProviders;

    @Override
    public SpecificationProvider<Book> findSpecification(SearchParam param) {
        return specificationProviders.stream()
                .filter(sp -> sp.getSearchParam().equals(param))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Can't find bean for param type: %s"
                                .formatted(param.name())));
    }
}
