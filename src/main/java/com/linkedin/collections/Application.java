package com.linkedin.collections;

import java.util.LinkedHashSet;
import java.util.Set;
public class Application {

	public static void main(String[] args) {
		
		Room piccadilly = new Room("Piccadilly", "Guest Room", 3, 125.00);
		Room oxford = new Room("Oxford", "Suite", 5, 225.0);
		Room oxfordDuplicate = new Room("Oxford", "Suite", 5, 225.0);
		Room victoria = new Room("Victoria", "Suite", 5, 225.00);

		Set<Room> rooms = new LinkedHashSet<>(); 
			//Order is not guarenteed
			//If we want it to have order, then we need
			//to define rooms as an instance of a linkedHashSet
		rooms.add(piccadilly);
		rooms.add(oxford);
		rooms.add(oxford);
		rooms.add(oxfordDuplicate);
		rooms.add(victoria);
		//Some new methods that were added to the set interface

		Set<Room> otherRooms = Set.of(piccadilly,oxford); 
		//.of method is used to create an unmodifiable Set instance
		// This can be helpful for thread safety or sometimes performance
		//.of method has several overloaded forms that can take in up to 9 elements

		rooms.stream()
			.map(r->r.getName())
			.forEach(r->System.out.println(r));

		//.copyOf
		Set<Room> moreRooms = Set.copyOf(rooms); 
		//unmodifiable set is backed by the first collection we copied
		moreRooms.stream()
			.map(r->r.getName())
			.forEach(System.out::println);


	}
}
