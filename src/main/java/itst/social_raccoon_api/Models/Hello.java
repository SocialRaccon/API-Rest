package itst.social_raccoon_api.Models;
public class Hello {
    private final String name;
    public Hello(String name) {
        this.name = name;
    }

    public String getName() {
        return "Hello " + name;
    }
}