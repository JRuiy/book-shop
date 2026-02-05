package mate.academy.bookshop.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.BookDto;
import mate.academy.bookshop.dto.CreateBookRequestDto;
import mate.academy.bookshop.dto.UpdateBookRequestDto;
import mate.academy.bookshop.exception.EntityNotFoundException;
import mate.academy.bookshop.mapper.BookMapper;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto book) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(book)));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
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
    public BookDto update(Long id, UpdateBookRequestDto book) {
        Book bookFromDb = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Can't find book with id: %s".formatted(String.valueOf(id))));
        bookMapper.toModel(bookFromDb, book);
        return bookMapper.toDto(bookRepository.save(bookFromDb));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
