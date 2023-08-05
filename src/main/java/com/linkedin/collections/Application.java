package com.linkedin.collections;

import java.util.Arrays;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;
public class Application {

	public static void main(String[] args) {
		
		List<Integer> numbers = Arrays.asList(500, 1500, 2500, 1000, 3000, 2000);
		
		//A TreeSet is a set implementation that uses a binary search tree
		//to organise elements. So the collection must have a way to 
		//compare whether an element is greater than, less than, or equal
		//To do this the tree set uses a comparators or elements that implements the comparator 
		//interface to performs relational comparison of elements that
		//
		//Unlike the other collection we've seen, the tree set uses the comparator to 
		//evaluate elements rather than the equals() method
		//Comparison methods
			//comparator
			//comparable
		
		NavigableSet<Integer> numberTree = new TreeSet<>(numbers);
		//[type<generic>] [variable name] = [keyword to instantiate new object] [Classname<Generic>][parenthesis to invoke constructor method] 


		//.descendingSet() returns a set that is in the descending order
		//.headSet(number) returns a set with elements that are less than number specified
		//.tailSet(number) returns a set with element that are greater than number specified
		//.subSet(n1, n2) returns a set with elements that are greater than n1 but less than n2
		//.lower(number) returns an element that is next lower than number specified
		//.higher(number) returns an element that is next higher than number specified
		System.out.println(numberTree.descendingSet().getClass());



	}
}
