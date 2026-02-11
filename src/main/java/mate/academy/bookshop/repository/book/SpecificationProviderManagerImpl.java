package mate.academy.bookshop.repository.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.repository.SpecificationProvider;
import mate.academy.bookshop.repository.SpecificationProviderManager;
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
