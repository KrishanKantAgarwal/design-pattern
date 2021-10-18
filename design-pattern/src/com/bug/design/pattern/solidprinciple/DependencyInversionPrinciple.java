package com.bug.design.pattern.solidprinciple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// A. High-level modules should not depend on low-level modules. 
// Both should depend on abstractions.

// B. Abstractions should not depend on details. 
// Details should depend on abstractions.

class Person {
	public String name;

	public Person(String name) {
		this.name = name;
	}
}

//Using Interface (Abstraction) so that the high level module should not depends on the low level module
interface RelationshipBrowser {
	List<Person> findAllChildrenOf(String name);
}

//Low Level module
class Relationships implements RelationshipBrowser {

	private List<Map<String, ArrayList<Person>>> relations = new ArrayList<>();

	public List<Map<String, ArrayList<Person>>> getRelations() {
		return relations;
	}

	public void addParentAndChild(String parent, ArrayList<Person> children) {
		HashMap<String, ArrayList<Person>> hm = new HashMap<>();
		hm.put(parent, children);
		relations.add(hm);
	}

	@Override
	public List<Person> findAllChildrenOf(String name) {
		for (Map<String, ArrayList<Person>> map : relations) {
			if(map.containsKey(name)) {
				return map.get(name);
			}
		}
		return null;
	}
}

//High Level module
class Research {
	public Research(RelationshipBrowser browser) {
		List<Person> children = browser.findAllChildrenOf("John");
		for (Person child : children)
			System.out.println("John has a child called " + child.name);
	}
}

class DependencyInversionPrinciple {
	public static void main(String[] args) {
		Person parent = new Person("John");
		Person child1 = new Person("Chris");
		Person child2 = new Person("Matt");
		
		ArrayList<Person> children = new ArrayList<Person>();
		children.add(child1);
		children.add(child2);

		// low-level module
		Relationships relationships = new Relationships();
		relationships.addParentAndChild(parent.name, children);

		new Research(relationships);
	}
}
