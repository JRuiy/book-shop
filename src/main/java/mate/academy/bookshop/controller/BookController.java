package mate.academy.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.BookDto;
import mate.academy.bookshop.dto.BookSearchParameters;
import mate.academy.bookshop.dto.CreateBookRequestDto;
import mate.academy.bookshop.dto.ResponseError;
import mate.academy.bookshop.service.BookService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book", description = "Book API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "Return list of books", description = "Return list of books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found the books")
    })
    @GetMapping
    public Page<BookDto> getAll(@ParameterObject Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @Operation(summary = "Create new Book", description = "Create new Book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "New Book is created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input provided",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@ParameterObject @RequestBody
                                  @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @Operation(summary = "Get Book by id", description = "Get Book by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Received Boo by id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Book not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),
    })
    @GetMapping("/{id}")
    public BookDto getBookById(@Parameter(description = "ID of the book", example = "1")
                                   @PathVariable Long id) {
        return bookService.findById(id);
    }

    @Operation(summary = "Delete Book by id", description = "Delete Book by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Received Book by id")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@Parameter(description = "ID of the book", example = "1")
                                   @PathVariable Long id) {
        bookService.deleteById(id);
    }

    @Operation(summary = "Update Book by id", description = "Update Book by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Updated book by id",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input provided",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),
    })
    @PutMapping("/{id}")
    public BookDto updateBookById(@Parameter(description = "ID of the book", example = "1")
                                  @PathVariable Long id,
                                  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                          description = "Schema to update book",
                                          required = true,
                                          content = @Content(
                                                  mediaType = "application/json",
                                                  schema = @Schema(implementation =
                                                          CreateBookRequestDto.class)))
                                  @ParameterObject
                                  @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @Operation(summary = "Search books by title or/and author",
            description = "Search books by title or/and author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Books found by search parameters")
    })
    @GetMapping("/search")
    public Page<BookDto> searchBooks(@ParameterObject BookSearchParameters searchParameters,
                                     @ParameterObject Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }
}
