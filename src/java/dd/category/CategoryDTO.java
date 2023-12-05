/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.category;

/**
 *
 * @author netbeans
 */
public class CategoryDTO {
    private long categoryId;
    private String name;

    public CategoryDTO(long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
