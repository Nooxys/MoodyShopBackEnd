package CiroVitiello.MoodyShopBackEnd.controllers;

import CiroVitiello.MoodyShopBackEnd.dto.NewArticleDTO;
import CiroVitiello.MoodyShopBackEnd.entities.Article;
import CiroVitiello.MoodyShopBackEnd.entities.User;
import CiroVitiello.MoodyShopBackEnd.exceptions.BadRequestException;
import CiroVitiello.MoodyShopBackEnd.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService as;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Article save(@RequestBody @Validated NewArticleDTO body, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.as.save(body);
    }

    @PutMapping("/{subId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Article findByIdAndUpdate(@PathVariable UUID artId, @RequestBody @Validated NewArticleDTO body, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.as.findByIdAndUpdate(artId, body);
    }

    @DeleteMapping("/{artId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID artId) {
        this.as.findByIdAndDelete(artId);
    }

    @GetMapping("/me")
    public Page<Article> getFavorites(@AuthenticationPrincipal User user,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return this.as.getArticlesByUser(user.getId(), pageable);
    }
}
