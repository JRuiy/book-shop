package mate.academy.bookshop.service;

import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.BookDto;
import mate.academy.bookshop.dto.BookSearchParameters;
import mate.academy.bookshop.dto.CreateBookRequestDto;
import mate.academy.bookshop.exception.EntityNotFoundException;
import mate.academy.bookshop.mapper.BookMapper;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.repository.book.BookRepository;
import mate.academy.bookshop.repository.book.BookSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    @Transactional
    public BookDto save(CreateBookRequestDto book) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(book)));
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toDto);
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't find book with id: %s".formatted(String.valueOf(id))));
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional
    public BookDto update(Long id, CreateBookRequestDto book) {
        Book bookFromDb = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Can't find book with id: %s".formatted(String.valueOf(id))));
        bookMapper.updateModel(bookFromDb, book);
        return bookMapper.toDto(bookRepository.save(bookFromDb));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookDto> search(BookSearchParameters searchParameters, Pageable pageable) {
        return bookRepository.findAll(bookSpecificationBuilder.build(searchParameters), pageable)
                .map(bookMapper::toDto);
    }
}
