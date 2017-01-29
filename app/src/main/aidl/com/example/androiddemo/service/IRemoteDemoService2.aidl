package com.example.androiddemo.service;
import com.example.androiddemo.service.Person;

interface IRemoteDemoService2 {
	String getQuote(in String ticker, in Person requester);
}