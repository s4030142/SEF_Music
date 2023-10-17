import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class ArtistTest {
	
	@Test
	public void TestAddArtistValidInput() {
		System.out.println("Test: addArtist valid input");
		clearFile();
		
		testAddArtist("569MMMRR_%", 
				"John Doe", 
				"Melbourne|Victoria|Australia", 
				"05-11-1980",
				"I am a singer and songwriter and I sing and write songs",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("pop","classical", "jazz")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")), true);
		
		assertEquals(countLines(), 1);
		
		testAddArtist("555ABCDE$?", 
				"John Doe", 
				"Sydney|New South Wales|Australia", 
				"01-01-2000",
				"One two three four five six seven I write songs",
				new ArrayList<String>(Arrays.asList("Songwriter")),
				new ArrayList<String>(Arrays.asList("pop", "jazz")), 
				new ArrayList<String>(Arrays.asList()), true);
		
		assertEquals(countLines(), 2);
		
		testAddArtist("999VWXYZ!#", 
				"John Doe", 
				"New York|New York|United States of America", 
				"31-12-2022",
				"One two three four five six seven eight nine ten eleven twelve fourteen fifteen sixteen seventeen I I I I I I I I I I I write songs",
				new ArrayList<String>(Arrays.asList("Songwriter", "Songwriter2", "Songwriter3", "Songwriter4", "Songwriter5")),
				new ArrayList<String>(Arrays.asList("rock", "jazz", "classical", "techno", "house")), 
				new ArrayList<String>(Arrays.asList("2021, Best Song Written For Visual Media", "2022, Best Song Written For Visual Media", "2023, Worst Song Written For Visual Media")), true);
		
		assertEquals(countLines(), 3);
	}
	
	@Test
	public void TestAddArtistInvalidID() {
		System.out.println("Test: addArtist invalid ID");
		clearFile();
		
		//ID to long
		testAddArtist("569MMMRR_%&", 
				"John Doe", 
				"Melbourne|Victoria|Australia", 
				"05-11-1980",
				"I am a singer and songwriter and I sing and write songs",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("pop","classical", "jazz")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")), false);
		
		//ID to short
		testAddArtist("569MMMRR_", 
				"John Doe", 
				"Melbourne|Victoria|Australia", 
				"05-11-1980",
				"I am a singer and songwriter and I sing and write songs",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("pop","classical", "jazz")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")), false);
		
		testAddArtist("AB12345678", 
				"John Doe", 
				"Melbourne|Victoria|Australia", 
				"05-11-1980",
				"I am a singer and songwriter and I sing and write songs",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("pop","classical", "jazz")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")), false);
	}
	
	@Test
	public void TestAddArtistInvalidDate() {
		System.out.println("Test: addArtist invalid date");
		clearFile();
		
		//no date
		testAddArtist("569MMMRR_%", 
				"John Doe", 
				"Melbourne|Victoria|Australia", 
				"",
				"I am a singer and songwriter and I sing and write songs",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("pop","classical", "jazz")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")), false);
		
		//wrong year position
		testAddArtist("569MMMRR_%", 
				"John Doe", 
				"Melbourne|Victoria|Australia", 
				"1980-05-11",
				"I am a singer and songwriter and I sing and write songs",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("pop","classical", "jazz")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")), false);
	}
	
	@Test
	public void TestAddArtistInvalidAddress() {
		System.out.println("Test: addArtist invalid address");
		clearFile();
		
		testAddArtist("569MMMRR_%", 
				"John Doe", 
				"Victoria|Australia", 
				"05-11-1980",
				"I am a singer and songwriter and I sing and write songs",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("pop","classical", "jazz")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")), false);
		
		testAddArtist("569MMMRR_%", 
				"John Doe", 
				"Melbourne|Victoria|Australia|Earth", 
				"05-11-1980",
				"I am a singer and songwriter and I sing and write songs",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("pop","classical", "jazz")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")), false);
	}
	
	@Test
	public void TestAddArtistInvalidBio() {
		System.out.println("Test: addArtist invalid bio");
		clearFile();
		
		testAddArtist("555ABCDE$?", 
				"John Doe", 
				"Sydney|New South Wales|Australia", 
				"01-01-2000",
				"One two three four five six I write songs",
				new ArrayList<String>(Arrays.asList("Songwriter")),
				new ArrayList<String>(Arrays.asList("pop", "jazz")), 
				new ArrayList<String>(Arrays.asList()), false);
		
		testAddArtist("999VWXYZ!#", 
				"John Doe", 
				"New York|New York|United States of America", 
				"31-12-2022",
				"One two three four five six seven eight nine ten eleven twelve fourteen fifteen sixteen seventeen I I I I I I I I I I I I I write songs",
				new ArrayList<String>(Arrays.asList("Songwriter", "Songwriter2", "Songwriter3", "Songwriter4", "Songwriter5")),
				new ArrayList<String>(Arrays.asList("rock", "jazz", "classical", "techno", "house")), 
				new ArrayList<String>(Arrays.asList("2021, Best Song Written For Visual Media", "2022, Best Song Written For Visual Media", "2023, Worst Song Written For Visual Media")), false);
		
		
	}
	
	@Test
	public void TestAddArtistInvalidOccupations() {
		System.out.println("Test: addArtist invalid occupations");
		clearFile();
		
		testAddArtist("555ABCDE$?", 
				"John Doe", 
				"Sydney|New South Wales|Australia", 
				"01-01-2000",
				"One two three four five six seven I write songs",
				new ArrayList<String>(Arrays.asList()),
				new ArrayList<String>(Arrays.asList("pop", "jazz")), 
				new ArrayList<String>(Arrays.asList()), false);
		
		testAddArtist("999VWXYZ!#", 
				"John Doe", 
				"New York|New York|United States of America", 
				"31-12-2022",
				"One two three four five six seven eight nine ten eleven twelve fourteen fifteen sixteen seventeen I I I I I I I I  I I I I write songs",
				new ArrayList<String>(Arrays.asList("Songwriter", "Songwriter2", "Songwriter3", "Songwriter4", "Songwriter5", "Songwriter6")),
				new ArrayList<String>(Arrays.asList("rock", "jazz", "classical", "techno", "house")), 
				new ArrayList<String>(Arrays.asList("2021, Best Song Written For Visual Media", "2022, Best Song Written For Visual Media", "2023, Worst Song Written For Visual Media")), false);
		
		
	}
	
	@Test
	public void TestAddArtistInvalidAwards() {
		System.out.println("Test: addArtist invalid awards");
		clearFile();
		
		testAddArtist("555ABCDE$?", 
				"John Doe", 
				"Sydney|New South Wales|Australia", 
				"01-01-2000",
				"One two three four five six seven I write songs",
				new ArrayList<String>(Arrays.asList("Songwriter")),
				new ArrayList<String>(Arrays.asList("pop", "jazz")), 
				new ArrayList<String>(Arrays.asList("2022, To short title")), false);
		
		testAddArtist("999VWXYZ!#", 
				"John Doe", 
				"New York|New York|United States of America", 
				"31-12-2022",
				"One two three four five six seven eight nine ten eleven twelve fourteen fifteen sixteen seventeen I I I I I I I I  I I I I write songs",
				new ArrayList<String>(Arrays.asList("Songwriter", "Songwriter2", "Songwriter3", "Songwriter4", "Songwriter5")),
				new ArrayList<String>(Arrays.asList("rock", "jazz", "classical", "techno", "house")), 
				new ArrayList<String>(Arrays.asList("2021, Best Song Written For Visual Media", "2022, Best Song Written For Visual Media", "2023, Worst Song Written For Visual Media", "2024, Best song written while time traveling")), false);
		
		
	}
	
	@Test
	public void TestAddArtistInvalidGenres() {
		System.out.println("Test: addArtist invalid genre");
		clearFile();
		
		//too few genres
		testAddArtist("569MMMRR_%", 
				"John Doe", 
				"Melbourne|Victoria|Australia", 
				"05-11-1980",
				"I am a singer and songwriter and I sing and write songs",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("classical")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")), false);
	
		//to many genres
		testAddArtist("569MMMRR_%", 
				"John Doe", 
				"Melbourne|Victoria|Australia", 
				"05-11-1980",
				"I am a singer and songwriter and I sing and write songs",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("rock", "jazz", "classical", "techno", "house", "country")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")), false);
	
		//invalid genre combination
		testAddArtist("569MMMRR_%", 
				"John Doe", 
				"Melbourne|Victoria|Australia", 
				"05-11-1980",
				"I am a singer and songwriter and I sing and write songs",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("pop", "rock")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")), false);
	}
	
	@Test
	public void TestUpdateArtistValidInput() {
		System.out.println("Test: updateArtist valid input");
		clearFile();
		testSetup();
		
		
		//change name of old john
		Artist johnOldNewName = new Artist("569MMMRR_%", 
				"Jane Doe", 
				"Melbourne|Victoria|Australia", 
				"05-11-1980",
				"I am a singer and songwriter and I sing and write songs",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("pop","classical", "jazz")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")));
		
		assertEquals(johnOldNewName.updateArtist(), true);
		
		//change Address
		Artist john2000NewAddress = new Artist("555ABCDE$?", 
				"John Doe", 
				"Berlin|Berlin|Germany", 
				"01-01-2000",
				"One two three four five six seven I write songs",
				new ArrayList<String>(Arrays.asList("Songwriter")),
				new ArrayList<String>(Arrays.asList("pop", "jazz")), 
				new ArrayList<String>(Arrays.asList()));
		
		assertEquals(john2000NewAddress.updateArtist(), true);
		
		//change Birthday
		Artist johnJungNewBirthday = new Artist("999VWXYZ!#", 
				"John Doe", 
				"New York|New York|United States of America", 
				"12-10-2023",
				"One two three four five six seven eight nine ten eleven twelve fourteen fifteen sixteen seventeen I I I I I I I I I I I write songs",
				new ArrayList<String>(Arrays.asList("Songwriter", "Songwriter2", "Songwriter3", "Songwriter4", "Songwriter5")),
				new ArrayList<String>(Arrays.asList("rock", "jazz", "classical", "techno", "house")), 
				new ArrayList<String>(Arrays.asList("2021, Best Song Written For Visual Media", "2022, Best Song Written For Visual Media", "2023, Worst Song Written For Visual Media")));
		
		assertEquals(johnJungNewBirthday.updateArtist(), true);
		
		//change description
		Artist johnOldNewDescription = new Artist("569MMMRR_%", 
				"Jane Doe", 
				"Melbourne|Victoria|Australia", 
				"05-11-1980",
				"Thats great that I am able to change the description",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("pop","classical", "jazz")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")));
		
		assertEquals(johnOldNewDescription.updateArtist(), true);
		
		//change Occupation
		Artist john2000NewOccupation = new Artist("555ABCDE$?", 
				"John Doe", 
				"Berlin|Berlin|Germany", 
				"01-01-2000",
				"One two three four five six seven I write songs",
				new ArrayList<String>(Arrays.asList("Songwriter", "Student")),
				new ArrayList<String>(Arrays.asList("pop", "jazz")), 
				new ArrayList<String>(Arrays.asList()));
		
		assertEquals(john2000NewOccupation.updateArtist(), true);
		
		//remove genre
		Artist johnJungRemoveGenre = new Artist("999VWXYZ!#", 
				"John Doe", 
				"New York|New York|United States of America", 
				"12-10-2023",
				"One two three four five six seven eight nine ten eleven twelve fourteen fifteen sixteen seventeen I I I I I I I I I I I write songs",
				new ArrayList<String>(Arrays.asList("Songwriter", "Songwriter2", "Songwriter3", "Songwriter4", "Songwriter5")),
				new ArrayList<String>(Arrays.asList("jazz", "classical", "techno", "house")), 
				new ArrayList<String>(Arrays.asList("2021, Best Song Written For Visual Media", "2022, Best Song Written For Visual Media", "2023, Worst Song Written For Visual Media")));
		
		assertEquals(johnJungRemoveGenre.updateArtist(), true);
		
		//change and add award
		Artist johnOldNewAward = new Artist("569MMMRR_%", 
				"John Doe2", 
				"Melbourne|Victoria|Australia", 
				"05-11-1980",
				"Thats great that I am able to change the description",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("pop","classical", "jazz")), 
				new ArrayList<String>(Arrays.asList("2022, Second Best Song Written For Visual Media", "1999, A really old award")));
		
		assertEquals(johnOldNewAward.updateArtist(), true);
		
	}
	
	@Test
	public void TestUpdateArtistInvalidOccupation() {
		System.out.println("Test: updateArtist invalid occupation");
		clearFile();
		testSetup();
		
		//delete all ocupations
		Artist johnJungNoOccupations = new Artist("999VWXYZ!#", 
				"John Doe", 
				"New York|New York|United States of America", 
				"31-12-2022",
				"One two three four five six seven eight nine ten eleven twelve fourteen fifteen sixteen seventeen I I I I I I I I I I I write songs",
				new ArrayList<String>(Arrays.asList()),
				new ArrayList<String>(Arrays.asList("rock", "jazz", "classical", "techno", "house")), 
				new ArrayList<String>(Arrays.asList("2021, Best Song Written For Visual Media", "2022, Best Song Written For Visual Media", "2023, Worst Song Written For Visual Media")));
		
		assertEquals(johnJungNoOccupations.updateArtist(), false);
		
		//set to many occupations
		Artist john2000ToManyJobs = new Artist("555ABCDE$?", 
				"John Doe", 
				"Sydney|New South Wales|Australia", 
				"01-01-2000",
				"One two three four five six seven I write songs",
				new ArrayList<String>(Arrays.asList("Songwriter", "Influencer", "Self-emplyed", "Barista", "DJ", "Vlogger")),
				new ArrayList<String>(Arrays.asList("pop", "jazz")), 
				new ArrayList<String>(Arrays.asList()));
		
		assertEquals(john2000ToManyJobs.updateArtist(), false);
		
		//to old to change occupations
		Artist johnOldToOld = new Artist("569MMMRR_%", 
				"John Doe", 
				"Melbourne|Victoria|Australia", 
				"05-11-1980",
				"I am a singer and songwriter and I sing and write songs",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter", "Mechanic")),
				new ArrayList<String>(Arrays.asList("pop","classical", "jazz")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")));
		
		assertEquals(johnOldToOld.updateArtist(), false);
	}
	
	private void testAddArtist(String id, String name, String address, String birthdate, String bio, ArrayList<String> occupations,
			ArrayList<String> genres, ArrayList<String> awards, boolean success) {
		
		int oldLineNum = countLines(); 
		Artist testArtist = new Artist(id, 
				name, 
				address, 
				birthdate,
				bio,
				occupations,
				genres, 
				awards);
		
		assertEquals(testArtist.addArtist(), success);
	}
	
	private int countLines() {
		int lineCount = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader("./Artist.txt"));
            
            String line = "";
            while ((line = br.readLine()) != null) {
            	System.out.print(line);
                lineCount++;
            }
            
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        System.out.print(lineCount);
        return lineCount;
	}
	
	private void clearFile() {
		try {
            FileWriter fileWriter = new FileWriter("./Artist.txt", false); // Set the second parameter to 'false' for overwrite mode

            // Truncate the file to zero length
            fileWriter.write("");

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void testSetup() {
		Artist johnOld = new Artist("569MMMRR_%", 
				"John Doe", 
				"Melbourne|Victoria|Australia", 
				"05-11-1980",
				"I am a singer and songwriter and I sing and write songs",
				new ArrayList<String>(Arrays.asList("Singer","Songwriter")),
				new ArrayList<String>(Arrays.asList("pop","classical", "jazz")), 
				new ArrayList<String>(Arrays.asList("2022, Best Song Written For Visual Media")));
		
		Artist john2000 = new Artist("555ABCDE$?", 
				"John Doe", 
				"Sydney|New South Wales|Australia", 
				"01-01-2000",
				"One two three four five six seven I write songs",
				new ArrayList<String>(Arrays.asList("Songwriter")),
				new ArrayList<String>(Arrays.asList("pop", "jazz")), 
				new ArrayList<String>(Arrays.asList()));
		
		Artist johnJung = new Artist("999VWXYZ!#", 
				"John Doe", 
				"New York|New York|United States of America", 
				"31-12-2022",
				"One two three four five six seven eight nine ten eleven twelve fourteen fifteen sixteen seventeen I I I I I I I I I I I write songs",
				new ArrayList<String>(Arrays.asList("Songwriter", "Songwriter2", "Songwriter3", "Songwriter4", "Songwriter5")),
				new ArrayList<String>(Arrays.asList("rock", "jazz", "classical", "techno", "house")), 
				new ArrayList<String>(Arrays.asList("2021, Best Song Written For Visual Media", "2022, Best Song Written For Visual Media", "2023, Worst Song Written For Visual Media")));
		
		johnOld.addArtist();
		john2000.addArtist();
		johnJung.addArtist();
	}
}
