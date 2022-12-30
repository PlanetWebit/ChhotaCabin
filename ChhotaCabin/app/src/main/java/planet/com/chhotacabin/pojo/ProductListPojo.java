package planet.com.chhotacabin.pojo;

public class ProductListPojo {

    String name;
    String image;
    String price;
    String id;
    String discount;
    String propertyType;
    String person_limit;

    public String getPerson_limit() {
        return person_limit;
    }

    public void setPerson_limit(String person_limit) {
        this.person_limit = person_limit;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

