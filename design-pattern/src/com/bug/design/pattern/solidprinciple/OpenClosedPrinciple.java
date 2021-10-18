package com.bug.design.pattern.solidprinciple;

import java.util.List;
import java.util.stream.Stream;

enum Color {
	RED, GREEN, BLUE;
}

enum Size {
	SMALL, MEDIUM, LARGE
}

class Product {
	public String name;
	public Color color;
	public Size size;
	public Product(String name, Color color, Size size) {
		super();
		this.name = name;
		this.color = color;
		this.size = size;
	}
	@Override
	public String toString() {
		return "Product [name=" + name + ", color=" + color + ", size=" + size + "]";
	}
	
}

class ProductFilterNotGood {
	
	public Stream<Product> filterByColor(List<Product> products, Color color){
		return products.stream().filter(p -> p.color.equals(color));
	}
	
	public Stream<Product> filterBySize(List<Product> products, Size size){
		return products.stream().filter(p -> p.size.equals(size));
	}
	
	public Stream<Product> filterBySizeAndColor(List<Product> products, Size size, Color color){
		return products.stream().filter(p -> p.color.equals(color) && p.size.equals(size));
	}
	
	/**
	 * Suppose the requirements comes and ask us to include a price filter, 
	 * then we are going to add a new filter inside this filter class which is already delivered to the client.
	 * It breaks the OCP principle. As the OCP principle tells that it is open for extension but closed for the 
	 * modification. To overcome the problem of adding a new filters in future, we will use Specification pattern.
	 */
}

//Better approach

//Interface to get the stream of T based on the filtering
interface Filter<T>{
	Stream<T> filter(List<T> items, Specification<T> spec);
}

//Interface to check whether the given specification is true or false
interface Specification<T>{
	boolean isSatisified(T item);
}

//Color Specific filter
class ColorSpecification implements Specification<Product>{

	private Color color;
	
	public ColorSpecification(Color color) {
		this.color = color;
	}
	
	@Override
	public boolean isSatisified(Product product) {
		return product.color == color;
	}
}

//Size Specific filter
class SizeSpecification implements Specification<Product>{

	private Size size;
	
	public SizeSpecification(Size size) {
		this.size = size;
	}
	
	@Override
	public boolean isSatisified(Product product) {
		return product.size == size;
	}
}

//To filter based on two specification such as based on color or size
class AndSpecification<T> implements Specification<T>{

	private Specification<T> first;
	private Specification<T> second;
	
	public AndSpecification(Specification<T> first, Specification<T> second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public boolean isSatisified(T item) {
		return first.isSatisified(item) && second.isSatisified(item);
	}
}

class ProductFilterBasedOnOCP implements Filter<Product>{

	@Override
	public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
		return items.stream().filter(item -> spec.isSatisified(item));
	}
}


public class OpenClosedPrinciple {
	public static void main(String[] args) {
		Product apple = new Product("Apple", Color.GREEN, Size.SMALL);
	    Product tree = new Product("Tree", Color.GREEN, Size.LARGE);
	    Product house = new Product("House", Color.BLUE, Size.LARGE);
	    Product bus = new Product("Bus", Color.RED, Size.MEDIUM);
	    
	    List<Product> products = List.of(apple, tree, house, bus);
	    
	    //Using voilation of OCP
	    ProductFilterNotGood pf = new ProductFilterNotGood();
	    pf.filterByColor(products, Color.RED).forEach(System.out::println);
	    pf.filterBySize(products, Size.LARGE).forEach(System.out::println);
	    pf.filterBySizeAndColor(products, Size.LARGE, Color.BLUE).forEach(System.out::println);
	    
	    //Using OCP based Filter
	    ProductFilterBasedOnOCP pfOCP = new ProductFilterBasedOnOCP();
	    pfOCP.filter(products, new ColorSpecification(Color.RED)).forEach(System.out::println);
	    pfOCP.filter(products, new SizeSpecification(Size.LARGE)).forEach(System.out::println);
	    
	    pfOCP.filter(products, new AndSpecification<>(
	    		new ColorSpecification(Color.BLUE),
	    		new SizeSpecification(Size.LARGE)
		)).forEach(System.out::println);
	}
}
