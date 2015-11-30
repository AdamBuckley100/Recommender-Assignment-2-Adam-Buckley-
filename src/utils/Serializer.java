package utils;

import models.Movie;
import models.Rating;
import models.User;

public interface Serializer
{
	void push(Object o);
	Object pop();
	void write() throws Exception;
	void read() throws Exception;
}