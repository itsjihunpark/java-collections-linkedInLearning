package com.linkedin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate; //a functional interface
import java.util.stream.Collectors;
import java.util.function.Consumer; //another functional interface we need to supply to forEach terminal operation;
import java.util.function.ToDoubleFunction;
public class Application {

	public static void main(String[] args) {

		Room cambridge = new Room("Cambridge", "Premiere Room", 4, 175.00);
		Room manchester = new Room("Manchester", "Suite", 5, 250.00);
		Room oxford = new Room("Oxford", "Suite", 5, 225.0);
		Room victoria = new Room("Victoria", "Suite", 5, 225.00);
		
		Collection<Room> rooms = new ArrayList<>(Arrays.asList(cambridge, oxford, victoria, manchester));
		Iterator<Room> iterator = rooms.iterator();
		/**
		 * METHOD 2: Modifying collection object while iterating
		 * 1. Get the iterator for our rooms collections
		 * 2. Create a while loop and use the hasNext() method which
		 * 	  returns a boolean value
		 */

		oxford.setPetFriendly(true);
		victoria.setPetFriendly(true);
		/*
		while(iterator.hasNext())
		{
			Room room = iterator.next();
			if(room.isPetFriendly()) {
				iterator.remove(); //notice here you remove using the iterator's remove method
			}
			
		}
		*/
			
		/**
		 * When beginning to iterate thorugh collections,
		 * most java developers encounter the Concurrent 
		 * Modification exception.
		 */ 

		 /**
		 * CME is thrown when the underlying collection is
		 * modified while we're iterating through it; thrown
		 * when we add, remove, or change the collection inside
		 * of the loop
		 * 
		 * This exception protects the collection from being changed
		 * by another thread while we're iterating through it.
		 * 
		 * However, the exception is still thrown on the single
		 * threaded code like ours. 
		 * 
		 * Changes to a collection during iteration can have unexpected
		 * results at runtime which is difficult to troubleshoot
		 * 
		 * The iterator attempts to protect us from that by failing fast
		 * 
		 * Below is what you can do if you still want to modify an element
		 * while iterating.
		 * We can collect, the elements that we want remove in a new collection
		 * 1. Create a new Collection outside of the for each loop where
		 * we are not iterating#
		 * 2. add all the elements you want to remove to a new collection
		 * 3. Use the bulk operator removeAll() to remove all that's included
		 * in the new collection.
		*/
		/*
	//Method 1
		Collection<Room> removeRooms = new ArrayList<>();
		for(Room room : rooms) {
			if(room.isPetFriendly()) {
				//rooms.remove(room); //throws concurrent mod exception
				//instead of trying to remove the room like above
				//We'll add the room that we want to remove into the new 
				//collection
				removeRooms.add(room);
			}
		}
		//Step 2
		rooms.removeAll(removeRooms);//removeAll is one of the bulk operators
		*/

		/**
		 * Method 3: Using Java Streams API 
		 * The streams API and lamdas added to Java 8 gave us
		 * a consise functional style of writing code.
		 * 
		 * The above method(method 1) uses the imperitive style
		 * of writing code.
		 * With the imperitive style we have to specify exactly 
		 * how we want the code to behave
		 * 
		 * So we need to write the code that does 1)the iteration,
		 * 2)performs the conditional check and 3)adds the room to
		 * be removed to the remove room collection.
		 * 
		 * Writing code with the functional style(separates data and logic separate) using streams, its 
		 * slightly different.
		 * It's more like telling the code what we want to achieve.
		 * Look at method 3 below
		 * 
		 * Think of streams as a pipeline that our elements flow through
		 * but it never stores them like a collections. The elements are always
		 * moving forward through the stream.
		 * 1. We need to find a source of elements that can flow through a 
		 * stream.
		 * We can find them in our collection. Invoke the stream() method to start
		 * the pipeline that the elements in our collection will flow through
		 * 
		 * On that pipeline, we can add operation:
		 * We will first add an intermediate operation named filter
		 * it allows us to perform a test on the elements flowing through
		 * the stream that will determine if they can continue on for further
		 * processing.
		 * 
		 * To perform that test, we use an object known as a predicate. A predicate
		 * is a functional interface. So we will create an instance of a predicate
		 * using an anonymous class.
		 * 
		 * We could add another intermediate operation, but instead, we will now use
		 * a terminal operation named forEach. A terminal operation is used to end the pipeline.
		 * 
		 * It is either going to return a result or modify the elements that are streamed
		 * into it.
		 * 
		 * When we use a forEach operation, we need to supply it with a 'Consumer' another 
		 * functional interface.
		 * We will again use an anonymous class to get an instance of a 'Consumer'.
		 */
		/*method 3 */
		/*
		rooms.stream()
			.filter(new Predicate<Room>(){
				@Override
				public boolean test(Room t)
				{
					System.out.format("Testing %s with result %b%n", t.getName(), t.isPetFriendly());
					return t.isPetFriendly(); //when true, the filter allows it to be further processed, otherwise, it blocks it
				}
			}) 
			.forEach(new Consumer<Room>() { //need to set the generic to Room, otherwise we need to cast every room object to call room methods
				// need to add its functional interface method as this anonymous
				//clss implements Consumer interface
				@Override
				public void accept(Room room){
					System.out.println(room.getName());
				}
			});
		*/
			//The above technique uses the functional programming style. However, it does not look very concise.
			//that is because we used the anonymous classes to instanciate a functional interface.
			//The below technique will use lamda expression which will make the code more concise.
			//Lambda is a function that only has parameters, an arrow, and body.
			//Anything like method name, or return type is unneccesory.

		/* 
		rooms.stream()
			.filter(t -> t.isPetFriendly()) //when true, the filter allows it to be further processed, otherwise, it blocks it
			.forEach(room->System.out.println(room.getName()));
		
		//We can make the above even more concise
		//We can replace the lambda with a method reference
		//Method reference allows us to pass a method into a function
		*/
		Collection<Room> petFriendlyRooms = new ArrayList<>();
		
		rooms.stream()
			.filter(Room::isPetFriendly) //'Room type::invoke isPetFriendly' Method reference can be used as a predicate for our filter operation
			.forEach(room->System.out.println(room.getName())); //terminal operation.
		
		//more stream operations
		// In general, its okay to work with external objects in a stream
		// It is also okay to modify objects inside your stream. BUT YOU SHOULD
		// NEVER DO BOTH
		//.collect() a terminal operation within our pipeline
		petFriendlyRooms=rooms.stream()
			.filter(Room::isPetFriendly)
			.collect(Collectors.toList()); 
			// this terminal operator accepts a 'Collector', and we get several Collectors on the
			// Collector's class via its static methods. Most commonly used is the Collectors.toList();
			// All the elements that are flowed into the collect operatoration will be collected and returned
			// as a List
		petFriendlyRooms.stream()
		//intermediate operator that takes in the element coming from the stream
		//and then apply a transformation on the element which determines, what element
		//flows out of the operation and further down the stream. In this case a string
			.map(r->r.getName().getClass())
			.forEach(System.out::println);
		Double total = petFriendlyRooms.stream()
			//applies a transformation on the element and passes double
			//down the stream
			.mapToDouble(new ToDoubleFunction<Room>()//.mapToDouble takes in an instance of a functional interface which is implemented using an anonymous class and then instantiated
			{
				public double applyAsDouble(Room room)
				{
					return room.getRate();
				}
			}) //also takes in a method reference
			.sum(); //return sum of all the doubles that are flowing into that operation
		Double totalWithLambda = petFriendlyRooms.stream()
			//applies a transformation on the element and passes double
			//down the stream
			.mapToDouble(r->r.getRate()) //also takes in a lamda expression which matches the method signature of the required functional interface
			.sum();
		Double totalWithMethodReference = petFriendlyRooms.stream() //also takes in method reference which matches the method signature of the method in the required functional interface
			.mapToDouble(Room::getRate)
			.sum();
		System.out.println(totalWithMethodReference);
		System.out.println(totalWithLambda);
		System.out.println(total);
		}
}
