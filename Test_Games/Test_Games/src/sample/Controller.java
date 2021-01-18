package sample;

import Category.Article.Article;
import Category.Article.Category;
import Category.Article.Category_;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Controller {
    public ComboBox<Category_> filterBox;
    public ListView<Article> articleListView;
    public Label idLabel;
    public Label priceLabel;
    public Label nameLabel;
    public Label currencyLabel;
    public Label stockLabel;

    public Gson g;
    public Category obj;

    public void initialize() {
        String result = "";

        try (BufferedReader br = new BufferedReader(new FileReader("webshop.json"))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }

            g = new Gson();
            obj = g.fromJson(result, Category.class);

            for (Category_ c : obj.getCategories()) {
                filterBox.getItems().add(c);
            }

            for (Article a : obj.getArticles()) {
                articleListView.getItems().add(a);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void articleListViewClicked(MouseEvent mouseEvent) {
        Article selected = articleListView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            try {
                if (selected.getStock().equals(0)) {
                    throw new ArticleNotAvailablce();
                }
            } catch (Exception ArticleNotAvailable) {
                System.out.println(ArticleNotAvailable.getMessage());
            } finally {
                idLabel.setText(selected.getId().toString());
                priceLabel.setText(selected.getPrice().toString());
                nameLabel.setText(selected.getName());
                currencyLabel.setText(selected.getCurrency());
                stockLabel.setText(selected.getStock().toString());
            }
        }
    }

    public void filterBoxSelected(ActionEvent actionEvent) {
        Category_ selected = filterBox.getValue();

        if (selected != null) {
            articleListView.getItems().clear();

            for (Article a : obj.getArticles()) {
                if (a.getCategory() == selected.getId()) {
                    articleListView.getItems().add(a);
                }
            }
        }
    }
}
