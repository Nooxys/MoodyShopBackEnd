package CiroVitiello.MoodyShopBackEnd.services;

import CiroVitiello.MoodyShopBackEnd.dto.NewArticleDTO;
import CiroVitiello.MoodyShopBackEnd.entities.Article;
import CiroVitiello.MoodyShopBackEnd.exceptions.NotFoundException;
import CiroVitiello.MoodyShopBackEnd.repositories.ArticleDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.UUID;

@Service
public class ArticleService {
    @Autowired
    private ArticleDAO ad;

    @Autowired
    private UserService us;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<Article> getArticles(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.ad.findAll(pageable);
    }

    public Article save(NewArticleDTO body) throws IOException {
        String url = (String) cloudinaryUploader.uploader().upload(body.cover().getBytes(), ObjectUtils.emptyMap()).get("url");
        Article newArt = new Article(body.title(), body.description(), body.price(), body.category(), body.quantity(), url);
        return this.ad.save(newArt);
    }

    public Article findById(UUID uuid) {
        return this.ad.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }

    public Article findByIdAndUpdate(UUID uuid, NewArticleDTO body) throws IOException {
        String url = (String) cloudinaryUploader.uploader().upload(body.cover().getBytes(), ObjectUtils.emptyMap()).get("url");
        Article found = this.findById(uuid);
        found.setTitle(body.title());
        found.setDescription(body.description());
        found.setPrice(body.price());
        found.setCategory(body.category());
        found.setQuantity(body.quantity());
        found.setCover(url);
        this.ad.save(found);
        return found;
    }

    public void findByIdAndDelete(UUID uuid) {
        Article found = this.findById(uuid);
        this.ad.delete(found);
    }


}
