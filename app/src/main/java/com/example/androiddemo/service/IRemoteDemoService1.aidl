package com.example.androiddemo.service;

interface IRemoteDemoService1 {

	double getQuote(String ticker);
	
	void setRemoteBinder(IBinder binder);
}