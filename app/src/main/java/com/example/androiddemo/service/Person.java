package com.example.androiddemo.service;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

	public int mAge = 0;
	public String mName = null;

	public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

		@Override
		public Person createFromParcel(Parcel source) {
			return new Person(source);
		}

		@Override
		public Person[] newArray(int size) {
			return new Person[size];
		}
	};

	public Person() {

	}

	public Person(Parcel in) {
		mAge = in.readInt();
		mName = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mAge);
		dest.writeString(mName);

	}
}
