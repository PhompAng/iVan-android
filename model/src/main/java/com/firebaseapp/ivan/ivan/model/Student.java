// StudentKt.java
package com.firebaseapp.ivan.ivan.model;

import com.google.firebase.database.PropertyName;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import kotlin.jvm.internal.Intrinsics;

public final class Student extends FirebaseModel {
	@NotNull
	private Address address;
	@NotNull
	private Location location;
	@NotNull
	private String car;
	@NotNull
	private Name name;
	@NotNull
	private String no;
	@NotNull
	private String text;
	@NotNull
	private String parent;
	@NotNull
	private String school;
	@NotNull
	private Map<String, Map<String, Student>> carHistory;
	private long timestamp;

	public Student(@NotNull Address address, @NotNull Location location, @NotNull String car, @NotNull Name name, @NotNull String no, @NotNull String text, @NotNull String parent, @NotNull String school, @NotNull Map<String, Map<String, Student>> carHistory, long timestamp) {
		this.address = address;
		this.location = location;
		this.car = car;
		this.name = name;
		this.no = no;
		this.text = text;
		this.parent = parent;
		this.school = school;
		this.carHistory = carHistory;
		this.timestamp = timestamp;
	}

	public Student() {
		this.address = new Address();
		this.location = new Location();
		this.car = "";
		this.name = new Name();
		this.no = "";
		this.text = "";
		this.parent = "";
		this.school = "";
		this.carHistory = new HashMap<>();
		this.timestamp = 0;
	}

	@NotNull
	public final String getFullName() {
		return name.getThFirst() + " " + name.getThLast();
	}

	@NotNull
	public final String fullName() {
		return name.getThFirst() + " " + name.getThLast();
	}

	@PropertyName("address")
	@NotNull
	public final Address getAddress() {
		return this.address;
	}

	@PropertyName("address")
	public final void setAddress(@NotNull Address var1) {
		Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
		this.address = var1;
	}

	@PropertyName("location")
	@NotNull
	public final Location getLocation() {
		return this.location;
	}

	@PropertyName("location")
	public final void setLocation(@NotNull Location var1) {
		Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
		this.location = var1;
	}

	@PropertyName("car")
	@NotNull
	public final String getCar() {
		return this.car;
	}

	@PropertyName("car")
	public final void setCar(@NotNull String var1) {
		Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
		this.car = var1;
	}

	@PropertyName("name")
	@NotNull
	public final Name getName() {
		return this.name;
	}

	@PropertyName("name")
	public final void setName(@NotNull Name var1) {
		Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
		this.name = var1;
	}

	@PropertyName("no")
	@NotNull
	public final String getNo() {
		return this.no;
	}

	@PropertyName("no")
	public final void setNo(@NotNull String var1) {
		Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
		this.no = var1;
	}

	@PropertyName("text")
	@NotNull
	public final String getText() {
		return this.text;
	}

	@PropertyName("text")
	public final void setText(@NotNull String var1) {
		Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
		this.text = var1;
	}

	@PropertyName("parent")
	@NotNull
	public final String getParent() {
		return this.parent;
	}

	@PropertyName("parent")
	public final void setParent(@NotNull String var1) {
		Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
		this.parent = var1;
	}

	@PropertyName("school")
	@NotNull
	public final String getSchool() {
		return this.school;
	}

	@PropertyName("school")
	public final void setSchool(@NotNull String var1) {
		Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
		this.school = var1;
	}

	@PropertyName("car_history")
	@NotNull
	public final Map<String, Map<String, Student>> getCarHistory() {
		return this.carHistory;
	}

	@PropertyName("car_history")
	public final void setCarHistory(@NotNull Map<String, Map<String, Student>> var1) {
		Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
		this.carHistory = var1;
	}

	@PropertyName("timestamp")
	public final long getTimestamp() {
		return this.timestamp;
	}

	@PropertyName("timestamp")
	public final void setTimestamp(long var1) {
		this.timestamp = var1;
	}

	public String toString() {
		return "Student(address=" + this.address + ", location=" + this.location + ", car=" + this.car + ", name=" + this.name + ", no=" + this.no + ", text=" + this.text + ", parent=" + this.parent + ", school=" + this.school + ", carHistory=" + this.carHistory + ", timestamp=" + this.timestamp + ")";
	}

	public int hashCode() {
		return (((((((((this.address != null ? this.address.hashCode() : 0) * 31 + (this.location != null ? this.location.hashCode() : 0)) * 31 + (this.car != null ? this.car.hashCode() : 0)) * 31 + (this.name != null ? this.name.hashCode() : 0)) * 31 + (this.no != null ? this.no.hashCode() : 0)) * 31 + (this.text != null ? this.text.hashCode() : 0)) * 31 + (this.parent != null ? this.parent.hashCode() : 0)) * 31 + (this.school != null ? this.school.hashCode() : 0)) * 31 + (this.carHistory != null ? this.carHistory.hashCode() : 0)) * 31 + (int)(this.timestamp ^ this.timestamp >>> 32);
	}

	public boolean equals(Object var1) {
		if (this != var1) {
			if (var1 instanceof Student) {
				Student var2 = (Student)var1;
				if (Intrinsics.areEqual(this.address, var2.address) && Intrinsics.areEqual(this.location, var2.location) && Intrinsics.areEqual(this.car, var2.car) && Intrinsics.areEqual(this.name, var2.name) && Intrinsics.areEqual(this.no, var2.no) && Intrinsics.areEqual(this.text, var2.text) && Intrinsics.areEqual(this.parent, var2.parent) && Intrinsics.areEqual(this.school, var2.school) && Intrinsics.areEqual(this.carHistory, var2.carHistory) && this.timestamp == var2.timestamp) {
					return true;
				}
			}

			return false;
		} else {
			return true;
		}
	}
}
