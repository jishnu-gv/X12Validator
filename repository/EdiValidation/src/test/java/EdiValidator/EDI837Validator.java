package EdiValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EDI837Validator {

	String ediFilePath = "D:\\Ramesh-Personal\\Selenium\\Files\\JV_202303220943Msingleclaimfix.x12";

	@Test

	public void EDI837Test() {
		EDI837Validator validator = new EDI837Validator();

		boolean isValid = validator.validateEDI837(ediFilePath);

		System.out.println("Executed Sucessfully");
		
		Assert.assertTrue(isValid, "EDI 837 file validation failed.");

	}

	public boolean validateEDI837(String filePath) {

		// Load and parse the EDI 837 file

		// Use PipeParser to parse X12 EDI file

		// Create a HapiContext
		// HapiContext context = new DefaultHapiContext();

		// Create a PipeParser
		// Parser parser = context.getPipeParser();

		String x12Data = null;
		try {
			x12Data = loadX12File(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(x12Data);

		/*
		 * String[] segments = x12Data.split("~"); // Assuming "~" is the segment
		 * delimiter
		 * 
		 * for (String segment : segments) { String[] elements = segment.split("\\*");
		 * // Assuming "*" is the element delimiter String segmentCode = elements[0]; //
		 * The first element is the segment code
		 * 
		 * System.out.println("Segment: " + segmentCode);
		 * System.out.println("Elements: " + Arrays.toString(elements)); }
		 */

		String[] segments = x12Data.split("~"); // Assuming "~" is the segment delimiter

		for (String x12Segment : segments) {
			// Check if the segment is an ISA segment
			if (x12Segment.startsWith("ISA")) {

				String isaSegment = x12Segment;
				System.out.println(isaSegment);

				/*
				 * // Split the ISA segment into elements String[] isaElements =
				 * x12Segment.split("\\*");
				 * 
				 * // Print the elements of the ISA segment System.out.println("ISA Elements:");
				 * for (int i = 0; i < isaElements.length; i++) { System.out.println("Element "
				 * + (i + 1) + ": " + isaElements[i]);
				 */

				boolean isValid = validateISASegment(isaSegment);

				System.out.println(isValid);

				if (isValid) {
					System.out.println("ISA segment is valid.");
				} else {
					System.out.println("ISA segment is not valid.");
				}
				
				
				// Additional processing or storage of ISA elements can be done here
			}

			if (x12Segment.contains("GS")) {

				String gsSegment = x12Segment;
				System.out.println(gsSegment);

				/*
				 * // Split the ISA segment into elements String[] isaElements =
				 * x12Segment.split("\\*");
				 * 
				 * // Print the elements of the ISA segment System.out.println("ISA Elements:");
				 * for (int i = 0; i < isaElements.length; i++) { System.out.println("Element "
				 * + (i + 1) + ": " + isaElements[i]);
				 */

				boolean isValid = validateGSSegment(gsSegment);

				System.out.println(isValid);

				if (isValid) {
					System.out.println("gs segment is valid.");
				} else {
					System.out.println("gs segment is not valid.");
				}

				// Additional processing or storage of ISA elements can be done here
			}

		}
		return true;

	}

	private String loadX12File(String filePath) throws IOException {
		try {
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8));
			String line;

			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(System.lineSeparator());
			}

			reader.close();
			return stringBuilder.toString();
		} catch (IOException e) {
			System.out.println("Error occurred while loading the X12 file: " + e.getMessage());
			return null;
		}
	}

	private static boolean validateISASegment(String isaSegment) {
		// Check if the segment starts with "ISA"
		if (!isaSegment.startsWith("ISA")) {
			System.out.println("ISA segment is valid ISA.");
			return false;
		}

		// Split the ISA segment into elements
		// String[] isaElements = isaSegment.split("(?<=\\G.{2})");

		String[] isaElements = isaSegment.split("\\*");

		// Validate the length of each element

		// Print the elements of the ISA segment
		System.out.println("ISA Elements:");
		for (int i = 0; i < isaElements.length; i++) {
			System.out.println("Element " + (i + 1) + ": " + isaElements[i]);
		}

		System.out.println(isaElements.length);

		if (isaElements.length != 17) {
			System.out.println("ISA segment is valid 17.");
			return false;
		}

		// Perform specific checks for each element
		// Example checks - you should adjust based on your requirements
		System.out.println(isaElements[12]);
		if (!isaElements[12].equals("00501")) {
			System.out.println("ISA segment is valid 00501.");
			return false;
		}

		// Add more validation checks as needed for other elements

		// If all checks pass, the ISA segment is considered valid
		return true;
	}

	private static boolean validateGSSegment(String gsSegment) {
		// Check if the segment starts with "GS"
		if (!gsSegment.contains("GS")) {
			return false;
		}

		// Split the ISA segment into elements
		// String[] isaElements = isaSegment.split("(?<=\\G.{2})");

		String[] gsElements = gsSegment.split("\\*");

		// Validate the length of each element

		System.out.println(gsElements.length);

		// Print the elements of the ISA segment

		System.out.println("GS Elements:");
		for (int i = 0; i < gsElements.length; i++) {
			System.out.println("Element " + (i + 1) + ": " + gsElements[i]);
		}

		// Validate the length of each element
		if (gsElements.length != 9) {
			return false;
		}

		// Perform specific checks for each element

		System.out.println(gsElements[0]);
		if (gsElements[0].equals("GS")) {
			System.out.println(gsElements[0]);
			System.out.println("GS Inside");
			return false; // GS01 should be "GS"
		}

		if (!gsElements[1].matches("[A-Z0-9]{2}")) {
			System.out.println("GS02");
			return false; // GS02 should be alphanumeric with 2 characters
		}
		return true;
	}

	// public static void main(String[] args) {

	// }
}

