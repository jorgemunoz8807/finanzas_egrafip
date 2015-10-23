package citmatel.cu.utils;

import java.text.Normalizer;

public class AccentsAndToLowerCase {

	public static String stripAccentsAndToLowerCase(String s)

	{
		s = Normalizer.normalize(s, Normalizer.Form.NFD);

		// s = s.replaceAll("[^\\p{ASCII}]", "");

		s = s.replaceAll("\\p{M}", ""); // for UNICODE

		s = s.toLowerCase();

		return s;

	}
}
